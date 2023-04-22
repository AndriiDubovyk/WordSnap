package com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard.view_model

sealed interface PracticeFlashcardAction {
    object GoToNextFlashcardPractice: PracticeFlashcardAction
    object PronounceWord: PracticeFlashcardAction
}