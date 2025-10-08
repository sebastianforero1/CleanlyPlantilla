package com.xacarana.myapplication.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object Notifications {
    const val CHANNEL_ID = "cleanly_reminders"

    fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Recordatorios de limpieza",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifica 1 hora antes de la actividad de limpieza."
            }
            manager.createNotificationChannel(channel)
        }
    }
}
