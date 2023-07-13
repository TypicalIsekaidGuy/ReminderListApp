package com.example.reminderlistapp.data.reminder

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Upsert
    suspend fun upsertReminder(reminder: Reminder)
    @Delete
    suspend fun deleteReminder(reminder: Reminder)
    @Query("SELECT * FROM reminder ORDER BY title ASC")
    fun getReminderByTitle(): Flow<List<Reminder>>
    @Query("SELECT * FROM reminder ORDER BY time ASC")
    fun getReminderByTime(): Flow<List<Reminder>>
}