package com.example.reminderlistapp.model

import com.example.reminderlistapp.data.reminder.Reminder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

sealed interface ReminderEvent {
    object SaveReminder: ReminderEvent
    data class SetTitle(val title: String): ReminderEvent
    data class SetTime(val time: LocalDateTime): ReminderEvent
    object ShowDialog: ReminderEvent
    object HideDialog: ReminderEvent
    data class SortReminders(val sortType: SortType): ReminderEvent
    data class DeleteReminder(val reminder: Reminder): ReminderEvent
}