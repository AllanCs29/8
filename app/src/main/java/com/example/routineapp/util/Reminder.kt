
package com.example.routineapp.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat

fun scheduleReminder(ctx: Context, title: String, hhmm: String) {
    ensureChannel(ctx)
}

private const val CH_ID = "routine_channel"

private fun ensureChannel(ctx: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val mgr = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (mgr.getNotificationChannel(CH_ID) == null) {
            val ch = NotificationChannel(CH_ID, "Routine", NotificationManager.IMPORTANCE_DEFAULT)
            ch.enableLights(true); ch.lightColor = Color.GREEN
            mgr.createNotificationChannel(ch)
        }
    }
}

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        ensureChannel(context)
        val mgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notif = NotificationCompat.Builder(context, CH_ID)
            .setSmallIcon(android.R.drawable.ic_notification_clear_all)
            .setContentTitle("RoutineApp")
            .setContentText(intent.getStringExtra("msg") ?: "Recuerda tu actividad")
            .build()
        mgr.notify((System.currentTimeMillis()%100000).toInt(), notif)
    }
}
