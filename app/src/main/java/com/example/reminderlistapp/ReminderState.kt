package com.example.reminderlistapp

import com.example.reminderlistapp.data.reminder.Reminder

data class ReminderState(
    val reminders: List<Reminder> = emptyList(),
    val title: String = "",
    val time: Int = 100,
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.TITLE
)