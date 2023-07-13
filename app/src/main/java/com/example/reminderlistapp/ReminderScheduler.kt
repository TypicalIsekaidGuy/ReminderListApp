package com.example.reminderlistapp

import com.example.reminderlistapp.data.reminder.Reminder

interface ReminderScheduler {
    fun schedule(item: Reminder)
    fun cancel(item: Reminder)
}