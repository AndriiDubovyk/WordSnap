package com.andriidubovyk.wordsnap.feature_flashcard.presentation.flashcards

import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.Flashcard

sealed class FlashcardsEvent {
    data class DeleteFlashcard(val flashcard: Flashcard): FlashcardsEvent()
    object RestoreFlashcard: FlashcardsEvent()
}