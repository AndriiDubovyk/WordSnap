package com.andriidubovyk.wordsnap.presentation.bottom_nav_bar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.andriidubovyk.wordsnap.presentation.navigation.Screen


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