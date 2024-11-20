package com.example.drugie_podejscie

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ThirdActivity : AppCompatActivity() {

    private var boundService: BoundService? = null
    private var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        // a) Automatyczne uruchamianie usługi przy tworzeniu aktywności
        Intent(this, BoundService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }

        val btn1 = findViewById<Button>(R.id.dupdup)
        val btn2 = findViewById<Button>(R.id.dupdup2)
    }

    // a) Połączenie z usługą
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BoundService.LocalBinder
            boundService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Odłącz usługę przy niszczeniu aktywności
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }
}
