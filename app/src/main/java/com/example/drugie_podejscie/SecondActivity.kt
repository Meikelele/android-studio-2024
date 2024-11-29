package com.example.drugie_podejscie

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.drugie_podejscie.fragments.FirstFragment
import com.example.drugie_podejscie.fragments.SecondFragment

class SecondActivity : AppCompatActivity() {

    private var isFirstFragmentRemoved = false
    private var isSecondFragmentRemoved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Load the first fragment by default
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FirstFragment())
            .commit()

        // Buttons for loading and removing fragments
        val f1Btn = findViewById<Button>(R.id.f1Btn)
        val f2Btn = findViewById<Button>(R.id.f2Btn)
        val removeFragmentBtn = findViewById<Button>(R.id.removeFragmentBtn)

        f1Btn.setOnClickListener {
            if (!isFirstFragmentRemoved) {
                replaceFragment(FirstFragment())
            } else {
                clearFrame()
            }
        }

        f2Btn.setOnClickListener {
            if (!isSecondFragmentRemoved) {
                replaceFragment(SecondFragment())
            } else {
                clearFrame()
            }
        }

        removeFragmentBtn.setOnClickListener {
            removeFragment()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun removeFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()

            // Update fragment removal state
            when (fragment) {
                is FirstFragment -> isFirstFragmentRemoved = true
                is SecondFragment -> isSecondFragmentRemoved = true
            }
        }
    }

    private fun clearFrame() {
        // Wyświetlenie pustego widoku
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, Fragment())
            .commit()
    }
}
