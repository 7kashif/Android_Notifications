package com.example.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.notifications.Constants.CHANNEL_ID
import com.example.notifications.Constants.CHANNEL_NAME
import com.example.notifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<TimerViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel() //we only have to do this once

//        val intent = Intent(this,MainActivity::class.java)
//        //here we will create a pending intent that will allow android to execute a piece of
//        // code from our app to open our app on notification click
//        val pendingIntent = TaskStackBuilder.create(this).run {
//            addNextIntentWithParentStack(intent)
//            getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
//        }
//
//        val notification = NotificationCompat.Builder(this,CHANNEL_ID)
//            .setContentTitle("Happy Notification")
//            .setContentText("This is a very happy notification.")
//            .setSmallIcon(R.drawable.ic_emoj)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
//            .build()
//
//        val manager = NotificationManagerCompat.from(this)

//        binding.notifyButton.setOnClickListener {
//            manager.notify(NOTIFICATION_ID,notification)
//        }

        binding.notifyButton.setOnClickListener {
            startMyService(0)
        }

        viewModel.timer.observeForever {
            binding.tvTimer.text = it.toString()
            startMyService(it)
        }

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startMyService(time: Int) {
        Intent(this, NotificationService::class.java).also {
            it.putExtra("TIMER_VALUE", time)
            startForegroundService(it)
        }
    }

    private fun stopMyService() {
        Intent(this, NotificationService::class.java).also {
            stopService(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMyService()
    }

}