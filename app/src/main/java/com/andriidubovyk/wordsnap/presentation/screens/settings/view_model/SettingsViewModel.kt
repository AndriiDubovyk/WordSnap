package com.andriidubovyk.wordsnap.presentation.screens.settings.view_model

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.presentation.screens.settings.utils.NotificationReceiver
import com.andriidubovyk.wordsnap.presentation.screens.settings.utils.Time
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val sharedPref = context.getSharedPreferences(
        application.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    private val _state = mutableStateOf(
        SettingsState(
            notificationsEnabled = sharedPref.getBoolean(context.getString(R.string.saved_notification_enabled), false),
            notificationsTime = Time(
                hour = sharedPref.getInt(context.getString(R.string.saved_notification_hour), 20),
                minute = sharedPref.getInt(context.getString(R.string.saved_notification_minute), 30)
            )
        )
    )
    val state: State<SettingsState> = _state



    // Notification alarm
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val alarmPendingIntent: PendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        Intent(context, NotificationReceiver::class.java),
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    private val context
        get() = getApplication<Application>()


    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.SetNotificationsEnabled -> processSetNotificationsEnabled(event.value)
            is SettingsEvent.SetNotificationsTime -> processSetNotificationsTime(event.time)
        }
    }

    private fun processSetNotificationsEnabled(value: Boolean) {
        _state.value = state.value.copy(
            notificationsEnabled = value
        )
        with (sharedPref.edit()) {
            putBoolean(context.getString(R.string.saved_notification_enabled), value)
            apply()
        }
        if (!value) cancelNotifications()
        if (value) scheduleNotifications(state.value.notificationsTime)

    }

    private fun processSetNotificationsTime(time: Time) {
        _state.value = state.value.copy(
            notificationsTime = time
        )
        with (sharedPref.edit()) {
            putInt(context.getString(R.string.saved_notification_hour), time.hour)
            putInt(context.getString(R.string.saved_notification_minute), time.minute)
            apply()
        }
        cancelNotifications()
        scheduleNotifications(time)
    }

    private fun scheduleNotifications(time: Time) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, time.hour)
            set(Calendar.MINUTE, time.minute)
        }
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmPendingIntent)
    }

    private fun cancelNotifications() {
        alarmManager.cancel(alarmPendingIntent)
    }

}