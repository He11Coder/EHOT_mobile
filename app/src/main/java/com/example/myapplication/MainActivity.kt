package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity()
{
    private var name: String? = ""
    private var token: String? = ""
    private var group: String? = ""
    private var role: String? = ""

    lateinit var greetingLabel: TextView
    lateinit var groupLabel: TextView
    lateinit var actionButton: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
        groupLabel = findViewById(R.id.groupLabel)
        greetingLabel = findViewById(R.id.greetingLabel)
        actionButton = findViewById(R.id.actionButton)

        name = intent.getStringExtra(Constants.NAME)
        token = intent.getStringExtra(Constants.TOKEN)
        group = intent.getStringExtra(Constants.GROUP)
        role = intent.getStringExtra(Constants.ROLE)

        //greetingLabel.text = "Hello, $name!"

        if (role == "student")
        {
            //groupLabel.text = "Your group is $group"
            actionButton.text = "Generate QR-code"

            actionButton.setOnClickListener{
                val intent = Intent(this@MainActivity, QRActivity::class.java)
                intent.putExtra(Constants.TOKEN, token)
                startActivity(intent)
            }

        }
        else
        {
            actionButton.text = "Scan QR-code"

            actionButton.setOnClickListener {

                val intent = Intent(this@MainActivity, CameraActivity::class.java)
                intent.putExtra(Constants.TOKEN, token)
                startActivity(intent)
            }
        }
    }
}