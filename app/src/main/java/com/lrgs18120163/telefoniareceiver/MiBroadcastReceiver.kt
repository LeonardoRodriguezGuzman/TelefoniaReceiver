package com.lrgs18120163.telefoniareceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast

class MiBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val numero = sharedPreferences.getString("numero", "")
        val mensaje = sharedPreferences.getString("mensaje", "")
        Log.d("SharedPreferences", "Número cargado: $numero, Mensaje cargado: $mensaje")

        if (state == TelephonyManager.EXTRA_STATE_RINGING) {
            val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if (incomingNumber == numero) {
                sendSMS(context, numero, mensaje)
            }
        }
    }

    private fun sendSMS(context: Context, numero: String?, mensaje: String?) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(numero, null, mensaje, null, null)
            Toast.makeText(context, "Mensaje enviado", Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Log.e("MiBroadcastReceiver", "Error al enviar SMS: ${e.message}")
            Toast.makeText(context, "Error al enviar SMS", Toast.LENGTH_SHORT).show()
        }
    }

}