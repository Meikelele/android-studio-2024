package com.example.drugie_podejscie

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.drugie_podejscie.fragments.FirstFragment
import com.example.drugie_podejscie.fragments.SecondFragment
import com.example.drugie_podejscie.fragments.ThirdFragment

class SecondActivity : AppCompatActivity() {

    private var isFirstFragmentRemoved = false
    private var isSecondFragmentRemoved = false

    private var isThirdFragmentAdded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Pierwszy fragment wysweitla sie domyslnie
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FirstFragment())
            .commit()

        // Przyciski do ladowania fragmentow i nie tylko
        val f1Btn = findViewById<Button>(R.id.f1Btn)
        val f2Btn = findViewById<Button>(R.id.f2Btn)
        val removeFragmentBtn = findViewById<Button>(R.id.removeFragmentBtn)
        val addFragmentBtn = findViewById<Button>(R.id.addFragmentBtn)

        f1Btn.setOnClickListener {
            if (!isFirstFragmentRemoved) {
                replaceFragment(FirstFragment())
            } else if (isThirdFragmentAdded) {
                replaceFragment(ThirdFragment())
            } else {
                clearFrame()
            }
        }

        f2Btn.setOnClickListener {
            if (!isSecondFragmentRemoved) {
                replaceFragment(SecondFragment())
            } else if (isThirdFragmentAdded) {
                replaceFragment(ThirdFragment())
            } else {
                clearFrame()
            }
        }

        removeFragmentBtn.setOnClickListener {
            removeFragment()
        }

        addFragmentBtn.setOnClickListener {
            if (!isThirdFragmentAdded) {
                addFragment(ThirdFragment())
            } else {
                replaceFragment(ThirdFragment())
            }
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

            when (fragment) {
                is FirstFragment -> isFirstFragmentRemoved = true
                is SecondFragment -> isSecondFragmentRemoved = true
                is ThirdFragment -> isThirdFragmentAdded = false
            }
        }
    }

    private fun clearFrame() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, Fragment())
            .commit()
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()

        if (fragment is ThirdFragment) {
            isThirdFragmentAdded = true
        }
    }
}
