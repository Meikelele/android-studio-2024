package com.example.drugie_podejscie

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.widget.Toast

// klasa może reagować na zdarzenia systemowe
class SmsReceiver : BroadcastReceiver() {

    // metoda wywolywana gdy Android rozpozna zdarzenie na ktore nasluchuje
    // context - dostep do Toast
    // intent - szczegoly SMS
    override fun onReceive(context: Context, intent: Intent) {
        // sprawdzenie czy doebrany sms
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            // pobieranie wiadomosci SMS
            val bundle = intent.extras
            if (bundle != null) {
                // lista obiektow typu SmsMessage
                val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)

                // iteracja po wiadomościach i wyświetlanie informacji
                messages.forEach { sms ->
                    val sender = sms.displayOriginatingAddress
                    val content = sms.messageBody
                    Toast.makeText(context, "SMS od: $sender\n \"$content\"", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}



