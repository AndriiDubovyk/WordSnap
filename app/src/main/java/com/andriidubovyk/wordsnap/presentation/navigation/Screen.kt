package com.andriidubovyk.wordsnap.presentation.navigation

sealed class Screen(val route: String) {
    object Flashcards: Screen("flashcards_screen")
    object AddEditFlashcard: Screen("add_edit_flashcard_screen")
    object Study: Screen("study_screen")
    object PracticeFlashcard: Screen("practice_flashcard_screen")
    object Account: Screen("account_screen")
    object Settings: Screen("settings_screen")
}
