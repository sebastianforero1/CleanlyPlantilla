package com.xacarana.myapplication.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.xacarana.myapplication.R
import com.xacarana.myapplication.model.Task
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

/**
 * Receiver que muestra la notificación cuando llega el recordatorio.
 */
class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Notifications.ensureChannel(context)

        val title = intent.getStringExtra("title") ?: "Recordatorio de tarea"
        val noti = NotificationCompat.Builder(context, Notifications.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Cleanly")
            .setContentText("En 1 hora: $title")
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify((0..100000).random(), noti)
    }
}

object ReminderScheduler {
    /**
     * Programa un recordatorio para una tarea 1 hora antes de su hora (si tiene).
     */
    fun scheduleOneHourBefore(context: Context, task: Task) {
        val t = task.time ?: return
        val triggerAtMillis = task.date
            .atTime(t)                           // LocalDateTime
            .minusHours(1)                       // restar 1h aquí (tipo seguro)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val now = System.currentTimeMillis()
        if (triggerAtMillis <= now) return

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("title", task.title)
        }
        val pi = PendingIntent.getBroadcast(
            context,
            task.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pi)
    }
}
