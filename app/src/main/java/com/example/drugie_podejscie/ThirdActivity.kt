package com.example.drugie_podejscie

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.Toast
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

        val btn1 = findViewById<Button>(R.id.goToListActivity)
        val btn2 = findViewById<Button>(R.id.getCounterFromBindService)

        // e ) wyswtietlanie countera
        btn2.setOnClickListener {
            if (isBound) {
                val counterValue = boundService?.getCounter() ?: 0
                Toast.makeText(this, "$counterValue", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Service is not bound -_-", Toast.LENGTH_SHORT).show()
            }
        }

        btn1.setOnClickListener {
            if (isBound) {
                // e ) odlaczenie uslui
                unbindService(serviceConnection)

                // e ) Zatrzymanie usługi
                stopService(Intent(this, BoundService::class.java))
            }
            isBound = false

            // e ) Przejście do nowej aktywność poprzez ListActivity
            val event = Intent(this, FourthActivity::class.java)
            startActivity(event)
        }
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
