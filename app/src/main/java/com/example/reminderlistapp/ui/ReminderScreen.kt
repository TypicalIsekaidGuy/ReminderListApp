package com.example.reminderlistapp

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reminderlistapp.model.ReminderEvent
import com.example.reminderlistapp.model.ReminderState
import com.example.reminderlistapp.model.SortType
import com.example.reminderlistapp.ui.AddReminderDialog

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReminderScreen(
    state: ReminderState,
    onEvent: (ReminderEvent) -> Unit,
    hasNotificationPermission: MutableState<Boolean>
) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission.value = isGranted
        }
    )
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ReminderEvent.ShowDialog)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission.value) {
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add reminder"
                )
            }
        },
    ) {
        if(state.isAddingContact) {
            AddReminderDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SortType.values().forEach { sortType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(ReminderEvent.SortReminders(sortType))
                                },
                            verticalAlignment = CenterVertically
                        ) {
                            RadioButton(
                                selected = state.sortType == sortType,
                                onClick = {
                                    onEvent(ReminderEvent.SortReminders(sortType))
                                }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            items(state.reminders) { reminder ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "${reminder.title}",
                            fontSize = 20.sp
                        )
                        Text(text = reminder.time.toString(), fontSize = 12.sp)
                    }
                    IconButton(onClick = {
                        onEvent(ReminderEvent.DeleteReminder(reminder))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete contact"
                        )
                    }
                }
            }
        }
    }
}