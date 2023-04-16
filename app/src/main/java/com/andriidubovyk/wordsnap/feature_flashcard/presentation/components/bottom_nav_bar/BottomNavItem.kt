package com.andriidubovyk.wordsnap.feature_flashcard.presentation.components.bottom_nav_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.ui.graphics.vector.ImageVector
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.util.Screen

sealed class BottomNavItem(
    var title: String,
    var icon: ImageVector,
    var screen_route: String
) {
    object Flashcards : BottomNavItem(
        title = "Flashcard",
        icon = Icons.Default.Dataset,
        screen_route = Screen.Flashcards.route
    )
    object Study: BottomNavItem(
        title = "Study",
        icon = Icons.Default.School,
        screen_route = Screen.Study.route
    )
    object Account: BottomNavItem(
        title = "Account",
        icon = Icons.Default.Person,
        screen_route = Screen.Account.route
    )
}
