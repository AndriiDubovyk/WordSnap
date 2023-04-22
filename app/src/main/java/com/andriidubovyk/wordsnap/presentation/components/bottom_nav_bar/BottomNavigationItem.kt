package com.andriidubovyk.wordsnap.presentation.components.bottom_nav_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.andriidubovyk.wordsnap.R
import com.andriidubovyk.wordsnap.presentation.navigation.Screen

sealed class BottomNavigationItem(
    var titleResourse: Int,
    var icon: ImageVector,
    var screen_route: String
) {
    object Flashcards : BottomNavigationItem(
        titleResourse = R.string.flashcards,
        icon = Icons.Default.Dataset,
        screen_route = Screen.Flashcards.route
    )
    object Study: BottomNavigationItem(
        titleResourse = R.string.study,
        icon = Icons.Default.School,
        screen_route = Screen.Study.route
    )
    object Account: BottomNavigationItem(
        titleResourse = R.string.account,
        icon = Icons.Default.Person,
        screen_route = Screen.Account.route
    )
    object Settings: BottomNavigationItem(
        titleResourse = R.string.settings,
        icon = Icons.Default.Settings,
        screen_route = Screen.Settings.route
    )
}
