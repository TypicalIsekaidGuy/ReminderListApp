package com.example.reminderlistapp.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reminderlistapp.model.ReminderEvent
import com.example.reminderlistapp.model.ReminderState
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AddReminderDialog(
    state: ReminderState,
    onEvent: (ReminderEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var pickedDate by remember {
        mutableStateOf(state.time.toLocalDate())
    }
    var pickedTime by remember {
        mutableStateOf(state.time.toLocalTime())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(ReminderEvent.HideDialog)
        },
        title = { Text(text = "Add reminder") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(ReminderEvent.SetTitle(it))
                    },
                    placeholder = {
                        Text(text = "Title", textAlign = TextAlign.Center)
                    },
                    textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp, color = Color.Black)
                )
                Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth()){
                    Text(formattedTime, fontSize = 20.sp, color = Color.Black, modifier = Modifier.clickable { timeDialogState.show() })
                    Text(formattedDate,fontSize = 20.sp, color = Color.Black,modifier = Modifier.clickable {  dateDialogState.show() })
                }
            }

            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(text = "Ok") {
                    }
                    negativeButton(text = "Cancel")
                }
            ) {
                datepicker(
                    initialDate = pickedDate,
                    title = "Pick a date"
                ) {
                    pickedDate = it
                    onEvent(ReminderEvent.SetTime(it.atTime(pickedTime)))
                }
            }
            MaterialDialog(
                dialogState = timeDialogState,
                buttons = {
                    positiveButton(text = "Ok") {
                    }
                    negativeButton(text = "Cancel")
                }
            ) {
                timepicker(
                    initialTime = pickedTime,
                    title = "Pick a time"
                ) {
                    pickedTime = it
                    onEvent(ReminderEvent.SetTime(pickedDate.atTime(it)))
                }
            }
        }
        ,
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(ReminderEvent.SaveReminder)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}