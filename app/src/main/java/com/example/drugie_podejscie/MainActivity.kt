package com.example.drugie_podejscie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.startNewActivityButton)
        val editText = findViewById<EditText>(R.id.editText)

        // Load saved text from SharedPreferences
        val sharedPreferences = getSharedPreferences("com.example.drugie_podejscie.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val savedText = sharedPreferences.getString("SAVED_TEXT", "")
        editText.setText(savedText)

        btn.setOnClickListener {
            // Save text to SharedPreferences
            val textToSave = editText.text.toString()
            val editor = sharedPreferences.edit()
            editor.putString("SAVED_TEXT", textToSave)
            editor.apply()

            // Start new activity
            val event = Intent(this, SecondActivity::class.java)
            startActivity(event)
        }
    }
}
