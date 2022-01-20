package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity()
{
    private var name: String? = ""
    private var token: String? = ""
    private var group: String? = ""
    private var role: String? = ""

    lateinit var greetingLabel: TextView
    lateinit var groupLabel: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        name = intent.getStringExtra(Constants.NAME)
        token = intent.getStringExtra(Constants.TOKEN)
        group = intent.getStringExtra(Constants.GROUP)
        role = intent.getStringExtra(Constants.ROLE)

        greetingLabel = findViewById(R.id.greetingLabel)
        greetingLabel.text = "Hello, $name!"

        if (role == "student")
        {
            groupLabel = findViewById(R.id.groupLabel)
            groupLabel.text = "Your group is $group"
        }
    }
}