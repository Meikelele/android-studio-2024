package com.example.drugie_podejscie

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    // unikalny identyfikator
    private val SMS_PERMISSION_CODE = 101

    //  Odbiornik
    private lateinit var smsReceiver: SmsReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Sprawdzanie i żądanie uprawnień do odbierania SMS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS),
                SMS_PERMISSION_CODE
            )
        }

        // Inicjalizacja i dynamiczna rejestracja odbiornika SMS
        smsReceiver = SmsReceiver()
        val intentFilter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        registerReceiver(smsReceiver, intentFilter)

        val btn = findViewById<Button>(R.id.startNewActivityButton)
        val editText = findViewById<EditText>(R.id.editText)

        // Załadowanie zapisanego tekstu z SharedPreferences
        val sharedPreferences = getSharedPreferences("com.example.drugie_podejscie.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val savedText = sharedPreferences.getString("SAVED_TEXT", "")
        editText.setText(savedText)

        btn.setOnClickListener {
            // Zapisanie tekstu do SharedPreferences
            val textToSave = editText.text.toString()
            sharedPreferences.edit().putString("SAVED_TEXT", textToSave).apply()

            // Przejście do nowej aktywności
            val event = Intent(this, SecondActivity::class.java)
            startActivity(event)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Uprawnienia przyznane ^^,", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Uprawnienia nie zostały przyznane T_T", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        // Wyrejestrowanie odbiornika
        super.onDestroy()
        unregisterReceiver(smsReceiver)
    }
}
