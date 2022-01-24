package com.example.myapplication

import android.os.Bundle
import android.widget.ImageView
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity()
{

    private var email_field: EditText? = null
    private var pass_field: EditText? = null
    private var passConfirm_field: EditText? = null
    private var registerReg_button: Button? = null

    private var email: String = ""
    private var role: String = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        email_field = findViewById(R.id.emailRegEdit)
        pass_field = findViewById(R.id.passwordRegEdit)
        passConfirm_field = findViewById(R.id.passwordConfirmEdit)
        registerReg_button = findViewById(R.id.registerRegButton)

        registerReg_button?.setOnClickListener {
            if (email_field?.text?.toString()?.trim()?.equals("")!!)
            {
                Toast.makeText(this, "Enter your email address", Toast.LENGTH_LONG).show()
            }
            else if (pass_field?.text?.toString()?.trim()?.equals("")!!)
            {
                Toast.makeText(this, "Enter your password", Toast.LENGTH_LONG).show()
            }
            else if (!(pass_field?.text?.toString()?.trim()?.equals(passConfirm_field?.text?.toString()?.trim())!!))
            {
                Toast.makeText(this, "You didn't confirm your password", Toast.LENGTH_LONG).show()
            }
            else
            {
                regUser()
            }
        }
    }

    private fun regUser()
    {
        var m_email: String = email_field?.text.toString()
        var m_password: String = pass_field?.text.toString()

        val retrofit = Retrofit.Builder().baseUrl("http://ehot.herokuapp.com").addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(APIService::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("email", m_email)
        jsonObject.put("password", m_password)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.registerUser(requestBody)

            withContext(Dispatchers.Main)
            {
                if (response.isSuccessful)
                {
                    if (response.body()?.status!!)
                    {
                        val items = response.body()?.account
                        if (items != null)
                        {
                            email = items.email
                            role = items.role

                            Toast.makeText(this@RegisterActivity,
                                      "Account have been created successfully\n" +
                                              "Now your role is student\n" +
                                              "If you are a teacher, you can ask the administrator to change your role", Toast.LENGTH_LONG).show()
                        }
                    }
                    else
                    {
                        Toast.makeText(this@RegisterActivity, response.body()?.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}