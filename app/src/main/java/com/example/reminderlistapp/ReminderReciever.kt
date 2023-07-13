package com.example.reminderlistapp

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class ReminderReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        println("$message")
/*        val builder = NotificationCompat.Builder(this, 0.toString())
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Alarm!")
            .setContentText("$message")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            // Set the intent that will fire when the user taps the notification
            .setAutoCancel(true)*/
    }
}