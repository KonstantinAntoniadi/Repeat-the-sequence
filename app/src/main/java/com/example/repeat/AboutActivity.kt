package com.example.repeat

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class AboutActivity : Activity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val buttonBackAbout: Button? = findViewById<Button>(R.id.buttonBackAbout)
        buttonBackAbout?.setOnClickListener {
            val intent = Intent(this@AboutActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}