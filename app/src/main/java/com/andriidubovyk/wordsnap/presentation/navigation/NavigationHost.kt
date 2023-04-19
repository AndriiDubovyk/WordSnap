package com.andriidubovyk.wordsnap.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.andriidubovyk.wordsnap.presentation.screens.account.AccountScreen
import com.andriidubovyk.wordsnap.presentation.bottom_nav_bar.StudyScreen
import com.andriidubovyk.wordsnap.presentation.screens.add_edit_flashcard.AddEditFlashcardScreen
import com.andriidubovyk.wordsnap.presentation.screens.flashcards.FlashcardScreen
import com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard.PracticeFlashcardScreen

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.Flashcards.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
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
        composable(
            route = Screen.PracticeFlashcard.route +
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
            PracticeFlashcardScreen(navController = navController)
        }
        composable(route = Screen.Account.route) {
            AccountScreen()
        }
    }
}