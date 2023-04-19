package com.andriidubovyk.wordsnap.presentation.bottom_nav_bar

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.andriidubovyk.wordsnap.presentation.components.bottom_nav_bar.BottomNavigationItem

@Composable
fun BottomNavigationPanel(navController: NavController) {
    val items = listOf(
        BottomNavigationItem.Flashcards,
        BottomNavigationItem.Study,
        BottomNavigationItem.Account,
        BottomNavigationItem.Settings
    )
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomAppBar {
        items.forEach {
            NavigationBarItem(
                selected = it.screen_route == backStackEntry.value?.destination?.route,
                onClick = {
                    navController.navigate(it.screen_route) {
                        launchSingleTop = true // Don't navigate if we are already at this tab
                    }
                          },
                icon = {
                    Icon(imageVector = it.icon, contentDescription = it.title)
                },
                label = {
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
        }
    }
}