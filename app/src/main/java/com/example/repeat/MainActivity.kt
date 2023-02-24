package com.example.repeat

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonNewGame = findViewById<Button>(R.id.buttonNewGame)
        buttonNewGame.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        val buttonAbout = findViewById<Button>(R.id.buttonAbout)
        buttonAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        val freePlay = findViewById<Button>(R.id.buttonFreePlay)
        freePlay.setOnClickListener {
            val intent = Intent(this, FreePlayActivity::class.java)
            startActivity(intent)
        }
    }
}