package com.example.reminderlistapp

import com.example.reminderlistapp.data.reminder.Reminder

sealed interface ReminderEvent {
    object SaveReminder: ReminderEvent
    data class SetTitle(val title: String): ReminderEvent
    data class SetTime(val time: Int): ReminderEvent
    object ShowDialog: ReminderEvent
    object HideDialog: ReminderEvent
    data class SortReminders(val sortType: SortType): ReminderEvent
    data class DeleteReminder(val reminder: Reminder): ReminderEvent
}