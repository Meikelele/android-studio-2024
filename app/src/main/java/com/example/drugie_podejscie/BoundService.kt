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

    override fun onCreate() {
        super.onCreate()
        // b) Uruchamianie osobnego wątku
        handlerThread = HandlerThread("BoundServiceThread")
        handlerThread.start()
        handler = Handler(handlerThread.looper)
    }

    override fun onBind(intent: Intent?): IBinder {
        // a) Wyświetl monit przy uruchomieniu usługi
        Toast.makeText(this, "Your bound service has been started", Toast.LENGTH_SHORT).show()
        // b) Dodaj zadania do wykonania w tle
        startBackgroundTask()
        return binder
    }

    private fun startBackgroundTask() {
        // zadanie w tle
        handler.post {
            while (true) {
                // wyswietlanie toasta co 5sekund
                Thread.sleep(5000)

                // przeniesienie wyników osobnego wątku do głównego za pomocą toasta
                runOnUiThread {
                    Toast.makeText(this, "Your bound service is still working", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Zatrzymanie wątku gdy usluga ostaje zniszczona
        handlerThread.quitSafely()
    }

    private fun runOnUiThread(task: () -> Unit) {
        Handler(mainLooper).post(task)
    }
}
