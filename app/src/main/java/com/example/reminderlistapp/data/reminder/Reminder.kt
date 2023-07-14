package com.example.reminderlistapp.data.reminder

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDateTime

@Entity
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val time: LocalDateTime,
    val title: String
)