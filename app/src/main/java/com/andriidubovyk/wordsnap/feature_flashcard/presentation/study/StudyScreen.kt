package com.andriidubovyk.wordsnap.feature_flashcard.presentation.bottom_nav_bar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.util.Screen


@Composable
fun StudyScreen(navController: NavController) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    navController.navigate(Screen.PracticeFlashcard.route)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
            ) {
                Text(
                    text = "Start",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Ready to start?",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}