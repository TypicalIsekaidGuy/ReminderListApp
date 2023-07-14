package com.example.reminderlistapp

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class ReminderApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            val name = getString(R.string.notification_channel_alarm)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(0.toString(), name, importance)
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}