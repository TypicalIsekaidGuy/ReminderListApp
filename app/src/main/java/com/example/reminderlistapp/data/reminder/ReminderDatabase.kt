package com.example.reminderlistapp.data.reminder

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Reminder::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class ReminderDatabase: RoomDatabase() {

    abstract val dao:ReminderDao
}