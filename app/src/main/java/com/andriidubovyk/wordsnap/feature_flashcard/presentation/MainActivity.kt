package com.andriidubovyk.wordsnap.feature_flashcard.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.add_edit_flashcard.AddEditFlashcardScreen
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.bottom_nav_bar.AccountScreen
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.bottom_nav_bar.StudyScreen
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.flashcards.FlashcardScreen
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.util.Screen
import com.andriidubovyk.wordsnap.ui.theme.WordSnapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordSnapTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Flashcards.route
                    ) {
                        composable(route = Screen.Flashcards.route) {
                            FlashcardScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditFlashcard.route +
                                    "?flashcardId={flashcardId}",
                            arguments = listOf(
                                navArgument(
                                    name = "flashcardId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddEditFlashcardScreen(navController = navController)
                        }
                        composable(route = Screen.Study.route) {
                            StudyScreen(navController = navController)
                        }
                        composable(route = Screen.Account.route) {
                            AccountScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}