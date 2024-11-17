package com.example.drugie_podejscie

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)


        // buttons for loading fragments
        val btn1 = findViewById<Button>(R.id.dupdup)
        val btn2 = findViewById<Button>(R.id.dupdup2)

//        btn1.setOnClickListener {
//
//        }
//
//        btn2.setOnClickListener {
//
//        }
    }
}