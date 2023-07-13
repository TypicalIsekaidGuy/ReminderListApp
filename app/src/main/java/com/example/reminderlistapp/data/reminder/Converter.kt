package com.example.reminderlistapp.data.reminder

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class Converter {

    @TypeConverter
    fun fromTimestamp(value: Long): LocalDateTime {
        return value.let {         LocalDateTime.ofInstant(
            Instant.ofEpochMilli(it),
            TimeZone.getDefault().toZoneId());   }
    }
    @TypeConverter
    fun dateToTimestamp(time: LocalDateTime): Long {
        return time.toEpochSecond(ZoneOffset.UTC)
    }
}