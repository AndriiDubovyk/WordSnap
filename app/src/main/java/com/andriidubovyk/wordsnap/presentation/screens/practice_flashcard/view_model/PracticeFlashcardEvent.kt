package com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard.view_model

sealed interface PracticeFlashcardEvent {
    object ShowAnswer: PracticeFlashcardEvent
    data class SelectAnswerRating(val value: UserAnswerRating): PracticeFlashcardEvent
    object PronounceWord: PracticeFlashcardEvent
    object GoToNext: PracticeFlashcardEvent
}