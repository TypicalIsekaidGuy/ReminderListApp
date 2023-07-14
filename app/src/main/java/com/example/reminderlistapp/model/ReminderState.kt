package com.example.reminderlistapp.model

import com.example.reminderlistapp.data.reminder.Reminder
import java.time.LocalDateTime

data class ReminderState(
    val reminders: List<Reminder> = emptyList(),
    val title: String = "",
    val time: LocalDateTime = LocalDateTime.now(),
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.TITLE
)