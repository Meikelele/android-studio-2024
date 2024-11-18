package com.example.drugie_podejscie

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import android.os.Handler
import android.os.HandlerThread

class BoundService : Service() {

    // Binder do komunikacji z aktywnością
    private val binder = LocalBinder()

    private lateinit var handlerThread: HandlerThread
    private lateinit var handler: Handler

    // Klasa Binder dla komunikacji z aktywnością
    inner class LocalBinder : Binder() {
        fun getService(): BoundService = this@BoundService
    }

    override fun onBind(intent: Intent?): IBinder {
        // a) Wyświetl monit przy uruchomieniu usługi
        Toast.makeText(this, "Your bound service has been started ^^,", Toast.LENGTH_SHORT).show()
        return binder
    }
}
