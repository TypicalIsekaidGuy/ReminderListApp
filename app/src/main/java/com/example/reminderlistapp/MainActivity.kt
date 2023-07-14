package com.example.reminderlistapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import com.example.reminderlistapp.ui.theme.ReminderListAppTheme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.reminderlistapp.broadcast.AndroidReminderScheduler
import com.example.reminderlistapp.data.reminder.ReminderDatabase

class MainActivity : ComponentActivity() {



    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ReminderDatabase::class.java,
            "reminder.db"
        ).build()
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scheduler = AndroidReminderScheduler(this.applicationContext)



        val context = LocalContext
        var hasNotificationPermission: MutableState<Boolean> = mutableStateOf(false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                hasNotificationPermission = mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                )
            } else hasNotificationPermission = mutableStateOf(true)


        val viewModel by viewModels<ReminderViewModel>(
            factoryProducer = {
                object : ViewModelProvider.Factory {
                    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                        return ReminderViewModel(db.dao, scheduler, hasNotificationPermission) as T
                    }
                }
            }
        )
        setContent {
            ReminderListAppTheme {
                val state by viewModel.state.collectAsState()
                ReminderScreen(state = state, onEvent = viewModel::onEvent, viewModel.canUseNotifications)
            }
            }
        }
}