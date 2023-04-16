package com.andriidubovyk.wordsnap.feature_flashcard.presentation.util

sealed class Screen(val route: String) {
    object FlashcardsScreen: Screen("flashcards_screen")
    object AddEditFlashcardScreen: Screen("add_edit_flashcard_screen")
}
