package com.andriidubovyk.wordsnap.presentation.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.presentation.screens.settings.components.SettingsRow
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
    val uriHandler = LocalUriHandler.current
    val privacyPolicyLink = stringResource(R.string.privacy_policy_link)
    var isNotificationTimeDialogOpened  by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize(),) {
        SettingsRow {
            Text(
                text = stringResource(R.string.enable_notifications),
                style = MaterialTheme.typography.titleMedium
            )
            Switch(
                checked = state.notificationsEnabled,
                onCheckedChange = {
                    viewModel.onEvent(SettingsEvent.SetNotificationsEnabled(it))
                }
            )
        }
        Divider()
        SettingsRow {
            Text(
                text = stringResource(R.string.notifications_time),
                style = MaterialTheme.typography.titleMedium
            )
            TimeDisplay(
                time = state.notificationsTime,
                onClick = { isNotificationTimeDialogOpened = true }
            )
        }
        Divider()
        SettingsRow(
            modifier = Modifier.clickable { uriHandler.openUri(privacyPolicyLink) }
        ) {
            Text(
                text = stringResource(R.string.privacy_policy),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Divider()
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
                    Text(
                        text = stringResource(R.string.save),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}