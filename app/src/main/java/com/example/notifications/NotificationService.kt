package com.example.notifications

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.notifications.Constants.NOTIFICATION_ID

class NotificationService : Service() {

    override fun onBind(p0: Intent?): IBinder? = null

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val pendingIntent = intent?.let { notificationIntent ->
            PendingIntent.getActivity(this, Constants.REQUEST_CODE, notificationIntent, 0)
        }
        val data = intent?.getIntExtra("TIMER_VALUE", 0)

        val notification: Notification = NotificationCompat.Builder(this, Constants.CHANNEL_ID)
            .setContentTitle("StopWatch")
            .setContentText(data.toString())
            .setSmallIcon(R.drawable.ic_emoj)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        startForeground(NOTIFICATION_ID, notification)

        return START_STICKY
    }

}