package com.andriidubovyk.wordsnap.feature_flashcard.presentation.practice_flashcard

import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.Flashcard
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.add_edit_flashcard.AddEditFlashcardEvent
import com.andriidubovyk.wordsnap.feature_flashcard.presentation.flashcards.FlashcardsEvent

sealed class PracticeFlashcardEvent {
    object ShowAnswer: PracticeFlashcardEvent()
    data class SelectAnswerRating(val value: UserAnswerRating): PracticeFlashcardEvent()
    object GoToNext: PracticeFlashcardEvent()
}