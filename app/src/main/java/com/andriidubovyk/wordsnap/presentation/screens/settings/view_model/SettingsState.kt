package com.andriidubovyk.wordsnap.presentation.screens.settings.view_model

import com.andriidubovyk.wordsnap.presentation.screens.settings.utils.Time

data class SettingsState(
    val notificationsEnabled: Boolean = false,
    val notificationsTime: Time = Time(21, 0)
)
