package com.example.drugie_podejscie

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStartNewActivity = findViewById<Button>(R.id.startNewActivityButton)
        val btnPolish = findViewById<Button>(R.id.buttonPolish)
        val btnEnglish = findViewById<Button>(R.id.buttonEnglish)

        // Do nowej aktywności
        btnStartNewActivity.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        // Zmiana języka na polski
        btnPolish.setOnClickListener {
            setLocale("pl")
        }

        // Zmiana języka na angielski
        btnEnglish.setOnClickListener {
            setLocale("en")
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.locale = locale

        resources.updateConfiguration(config, resources.displayMetrics)

        // Restart aktywności, aby zaktualizować język
        val refresh = Intent(this, MainActivity::class.java)
        startActivity(refresh)
        finish()
    }
}
