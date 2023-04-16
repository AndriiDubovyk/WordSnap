package com.andriidubovyk.wordsnap.feature_flashcard.presentation.bottom_nav_bar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun StudyScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavigationPanel(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Study Screen",
            )
        }
    }
}