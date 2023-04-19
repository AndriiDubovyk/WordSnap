package com.andriidubovyk.wordsnap.presentation.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.andriidubovyk.wordsnap.presentation.screens.settings.components.TimeDisplay
import com.andriidubovyk.wordsnap.presentation.screens.settings.utils.Time
import com.andriidubovyk.wordsnap.presentation.screens.settings.view_model.SettingsEvent
import com.andriidubovyk.wordsnap.presentation.screens.settings.view_model.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    var isNotificationTimeDialogOpened  by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Enable notifications")
            Switch(
                checked = state.notificationsEnabled,
                onCheckedChange = {
                    viewModel.onEvent(SettingsEvent.SetNotificationsEnabled(it))
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Notification time")
            TimeDisplay(
                time = state.notificationsTime,
                onClick = { isNotificationTimeDialogOpened = true }
            )
        }
    }
    
    if (isNotificationTimeDialogOpened) {
        AlertDialog(onDismissRequest = { isNotificationTimeDialogOpened = false }) {
            val timePickerState by remember { mutableStateOf(
                TimePickerState(
                    initialHour = state.notificationsTime.hour,
                    initialMinute = state.notificationsTime.minute,
                    is24Hour = true
                )
            ) }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = timePickerState)
                Button(onClick = {
                    viewModel.onEvent(
                        SettingsEvent.SetNotificationsTime(
                            Time(timePickerState.hour, timePickerState.minute)
                        )
                    )
                    isNotificationTimeDialogOpened = false
                }) {
                    Text("Save")
                }
            }
        }
    }
}