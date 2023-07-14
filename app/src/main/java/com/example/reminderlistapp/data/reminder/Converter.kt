package com.example.reminderlistapp.data.reminder

import androidx.room.TypeConverter
import java.time.*
import java.util.*

class Converter {

    @TypeConverter
    fun fromTimestamp(value: Long): LocalDateTime {
        return value.let {
            val currentDateTime = LocalDateTime.now()
            val instant = currentDateTime.atZone(ZoneId.systemDefault()).toInstant()
            val newInstant = Instant.ofEpochSecond(instant.epochSecond + value)
            return LocalDateTime.ofInstant(newInstant, ZoneId.systemDefault())
        }
    }
    @TypeConverter
    fun dateToTimestamp(time: LocalDateTime): Long {
        return Duration.between(LocalDateTime.now(), time).seconds
    }
}