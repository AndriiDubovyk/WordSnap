package com.andriidubovyk.wordsnap.feature_flashcard.presentation.bottom_nav_bar

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.components.bottom_nav_bar.BottomNavItem

@Composable
fun BottomNavigationPanel(navController: NavController) {
    val items = listOf(
        BottomNavItem.Flashcards,
        BottomNavItem.Study,
        BottomNavItem.Account,
    )
    BottomAppBar() {
        items.forEach {
            NavigationBarItem(
                selected = navController.currentDestination?.route == it.screen_route,
                onClick = { navController.navigate(it.screen_route) },
                icon = {
                    Icon(imageVector = it.icon, contentDescription = it.title)
                },
                label = {
                    Text(it.title)
                }
            )
        }
    }
}