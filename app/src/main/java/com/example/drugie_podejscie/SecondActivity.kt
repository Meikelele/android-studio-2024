package com.example.drugie_podejscie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.drugie_podejscie.fragments.FirstFragment
import com.example.drugie_podejscie.fragments.SecondFragment
import android.widget.Button

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Load the first fragment by default
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FirstFragment())
            .commit()

        // buttons for loading fragments
        val f1Btn = findViewById<Button>(R.id.f1Btn)
        val f2Btn = findViewById<Button>(R.id.f2Btn)

        f1Btn.setOnClickListener {
            replaceFragment(FirstFragment())
        }

        f2Btn.setOnClickListener {
            replaceFragment(SecondFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
