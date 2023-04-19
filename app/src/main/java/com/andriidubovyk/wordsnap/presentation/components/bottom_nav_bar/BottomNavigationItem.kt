package com.andriidubovyk.wordsnap.presentation.components.bottom_nav_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.ui.graphics.vector.ImageVector
import com.andriidubovyk.wordsnap.presentation.navigation.Screen

sealed class BottomNavigationItem(
    var title: String,
    var icon: ImageVector,
    var screen_route: String
) {
    object Flashcards : BottomNavigationItem(
        title = "Flashcards",
        icon = Icons.Default.Dataset,
        screen_route = Screen.Flashcards.route
    )
    object Study: BottomNavigationItem(
        title = "Study",
        icon = Icons.Default.School,
        screen_route = Screen.Study.route
    )
    object Account: BottomNavigationItem(
        title = "Account",
        icon = Icons.Default.Person,
        screen_route = Screen.Account.route
    )
}
