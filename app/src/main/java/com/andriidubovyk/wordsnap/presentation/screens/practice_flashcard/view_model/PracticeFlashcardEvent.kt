package com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard.view_model

sealed class PracticeFlashcardEvent {
    object ShowAnswer: PracticeFlashcardEvent()
    data class SelectAnswerRating(val value: UserAnswerRating): PracticeFlashcardEvent()
    object GoToNext: PracticeFlashcardEvent()
}