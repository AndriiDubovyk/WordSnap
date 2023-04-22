package com.andriidubovyk.wordsnap.presentation.screens.settings.view_model

import com.andriidubovyk.wordsnap.presentation.screens.settings.utils.Time

sealed interface SettingsEvent {
    data class SetNotificationsEnabled(val value: Boolean): SettingsEvent
    data class SetNotificationsTime(val time: Time): SettingsEvent
}