package com.example.reminderlistapp.broadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.reminderlistapp.R

class ReminderService: Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        Log.d("YES","HELLO")
        val message = intent?.getStringExtra("EXTRA_MESSAGE")
        val notification = createNotification(message!!)
        Log.d("s",isNotificationChannelExists(0.toString(),this).toString())
        val notif = NotificationCompat.Builder(this, 0.toString())
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Alarm!")
            .setContentText("$message")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            // Set the intent that will fire when the user taps the notification
            .setAutoCancel(true)
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(notif.hashCode(),notif.build())
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notif.hashCode(), notif.build())
        }
        stopSelf()

        // Return the desired behavior for the service (e.g., START_STICKY, START_NOT_STICKY)
        return Service.START_NOT_STICKY
    }
    fun isNotificationChannelExists(channelId: String, context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = notificationManager.getNotificationChannel(channelId)
            return channel != null
        }
        return false
    }
    private fun createNotification(message: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, 0.toString())
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("Alarm!")
        .setContentText("$message")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        // Set the intent that will fire when the user taps the notification
        .setAutoCancel(true)
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
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}