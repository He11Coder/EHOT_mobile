package com.example.myapplication

import android.content.Intent
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
import retrofit2.converter.gson.GsonConverterFactory

class AuthActivity : AppCompatActivity()
{

    private var email_field: EditText? = null
    private var pass_field: EditText? = null
    private var logIn_button: Button? = null

    private var token: String = ""
    private var email: String = ""
    private var role: String = ""
    private var name: String = ""
    private var group: String? = ""
    private var year: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)

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
                authUser()
            }
        }
    }

    private fun authUser()
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
            val response = service.logIn(requestBody)

            withContext(Dispatchers.Main)
            {
                if (response.isSuccessful)
                {
                    if (response.body()?.status == "true")
                    {
                        val items = response.body()?.account
                        if (items != null)
                        {
                            email = items.email
                            token = items.token
                            role = items.role
                            getUserInfo()
                        }
                    }
                    else
                    {
                        Toast.makeText(this@AuthActivity, response.body()?.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun getUserInfo()
    {
        val retrofit = Retrofit.Builder().baseUrl("http://ehot.herokuapp.com").addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {

            val response = service.getInfo("basic $token")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful)
                {
                    if (response.body()?.status == "true")
                    {
                        val items = response.body()?.data
                        if (items != null)
                        {
                            if (role == "student")
                            {
                                name = items.name
                                group = items.group
                                year = items.year

                                val intent = Intent(this@AuthActivity, MainActivity::class.java)
                                intent.putExtra(Constants.NAME, name)
                                intent.putExtra(Constants.GROUP, group)
                                intent.putExtra(Constants.TOKEN, token)
                                intent.putExtra(Constants.ROLE, role)
                                startActivity(intent)
                            }
                            else if (role == "teacher")
                            {
                                name = items.name

                                val intent = Intent(this@AuthActivity, MainActivity::class.java)
                                intent.putExtra(Constants.NAME, name)
                                intent.putExtra(Constants.ROLE, role)
                                startActivity(intent)
                            }
                            else
                            {
                                Toast.makeText(this@AuthActivity,"Invalid role. Сontact the administrator",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(this@AuthActivity, response.body()?.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    public fun getToken(): String
    {
        return token
    }

    public fun getName(): String
    {
        return name
    }

    public fun getGroup(): String?
    {
        return group
    }
}