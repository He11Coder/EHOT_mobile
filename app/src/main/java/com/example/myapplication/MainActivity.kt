package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit

class MainActivity : AppCompatActivity()
{

    private var email_field: EditText? = null
    private var pass_field: EditText? = null
    private var logIn_button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email_field = findViewById(R.id.emailEdit)
        pass_field = findViewById(R.id.passwordEdit)
        logIn_button = findViewById(R.id.logInButton)

        logIn_button?.setOnClickListener {
            if (email_field?.text?.toString()?.trim()?.equals("")!!)
            {
                Toast.makeText(this, "Enter your email address", Toast.LENGTH_LONG).show()
            }
            else if (pass_field?.text?.toString()?.trim()?.equals("")!!)
            {
                Toast.makeText(this, "Enter your password", Toast.LENGTH_LONG).show()
            }
            else
            {
                var m_email: String = email_field?.text.toString()
                var m_password: String = pass_field?.text.toString()

                val retrofit = Retrofit.Builder()
                    .baseUrl("http://ehot.herokuapp.com")
                    .build()

                val service = retrofit.create(APIService::class.java)

                val jsonObject = JSONObject()
                jsonObject.put("email", m_email)
                jsonObject.put("password", m_password)

                val jsonObjectString = jsonObject.toString()
                val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

                CoroutineScope(Dispatchers.IO).launch {
                    val response = service.logIn(requestBody)

                    /*withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {

                            // Convert raw JSON to pretty JSON using GSON library
                            val gson = GsonBuilder().setPrettyPrinting().create()
                            val prettyJson = gson.toJson(
                                JsonParser.parseString(
                                    response.body()
                                        ?.string()
                                )
                            )

                            Log.d("Pretty Printed JSON :", prettyJson)

                        } else {

                            Log.e("RETROFIT_ERROR", response.code().toString())

                        }
                    }*/
                }
            }
        }
    }
}