package com.example.reminderlistapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.reminderlistapp.data.reminder.Reminder
import java.time.ZoneId

class AndroidReminderScheduler(
    private val context: Context
): ReminderScheduler {


    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: Reminder) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", item.title)
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(item: Reminder) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                Intent(context, ReminderReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}