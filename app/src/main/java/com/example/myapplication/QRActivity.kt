package com.example.myapplication

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QRActivity : AppCompatActivity()
{
    private var iv: ImageView? = null

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr_activity)
        iv = findViewById(R.id.iv) as ImageView

        getCode()
    }

    private fun TextToImageEncode(Value: String): Bitmap? {
        val bitMatrix: BitMatrix
        try {
            bitMatrix = MultiFormatWriter().encode(
                Value,
                BarcodeFormat.QR_CODE,
                500, 500, null
            )

        } catch (Illegalargumentexception: IllegalArgumentException) {

            return null
        }

        val bitMatrixWidth = bitMatrix.getWidth()

        val bitMatrixHeight = bitMatrix.getHeight()

        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth

            for (x in 0 until bitMatrixWidth) {

                pixels[offset + x] = if (bitMatrix.get(x, y))
                    resources.getColor(R.color.black)
                else
                    resources.getColor(R.color.white)
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight)
        return bitmap
    }

    private fun getCode()
    {
        val retrofit = Retrofit.Builder().baseUrl("http://ehot.herokuapp.com").addConverterFactory(
            GsonConverterFactory.create()).build()
        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {

            val response = service.getQR("basic ${intent.getStringExtra(Constants.TOKEN)}")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val items = response.body()
                    if (items!!.status) {
                        if (items != null) {
                            token = items.token
                            iv!!.setImageBitmap(TextToImageEncode(token!!))
                        }
                    } else
                    {
                        token = null
                        Toast.makeText(this@QRActivity, items.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}