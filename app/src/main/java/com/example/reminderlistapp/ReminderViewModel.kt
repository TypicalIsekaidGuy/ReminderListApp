package com.example.reminderlistapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderlistapp.data.reminder.Reminder
import com.example.reminderlistapp.data.reminder.ReminderDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ReminderViewModel(
    private val dao: ReminderDao
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.TITLE)
    private val _reminders = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.TITLE -> dao.getReminderByTitle()
                SortType.TIME -> dao.getReminderByTime()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(ReminderState())
    val state = combine(_state, _sortType, _reminders) { state, sortType, reminders ->
        state.copy(
            reminders = reminders,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ReminderState())

    fun onEvent(event: ReminderEvent) {
        when(event) {
            is ReminderEvent.DeleteReminder -> {
                viewModelScope.launch {
                    dao.deleteReminder(event.reminder)
                }
            }
            ReminderEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingContact = false
                ) }
            }
            ReminderEvent.SaveReminder -> {
                val title = state.value.title
                val time = state.value.time

                if(title.isBlank() || time<1) {
                    return
                }

                val reminder = Reminder(
                    title = title,
                    time = time
                )
                viewModelScope.launch {
                    dao.upsertReminder(reminder)
                }
                _state.update { it.copy(
                    isAddingContact = false,
                    title = "",
                    time = 100
                ) }
            }
            is ReminderEvent.SetTitle -> {
                _state.update { it.copy(
                    title = event.title
                ) }
            }
            is ReminderEvent.SetTime -> {
                _state.update { it.copy(
                    time = event.time
                ) }
            }
            ReminderEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingContact = true
                ) }
            }
            is ReminderEvent.SortReminders -> {
                _sortType.value = event.sortType
            }
        }
    }
}