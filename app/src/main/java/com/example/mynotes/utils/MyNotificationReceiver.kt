package com.example.mynotes.utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.mynotes.R

class MyNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "channel_01" // Same as the one you created earlier
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("My Notification Title")
            .setContentText("Your notification content here")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationId = intent?.getIntExtra("notification_id", 0) ?: 0
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}