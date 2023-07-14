package com.example.reminderlistapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.reminderlistapp.broadcast.ReminderService

class ReminderReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("GOT IT","YES")
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        val serviceIntent = Intent(context, ReminderService::class.java)
            .putExtra("EXTRA_MESSAGE",message)

        context?.startService(serviceIntent)
    }
}