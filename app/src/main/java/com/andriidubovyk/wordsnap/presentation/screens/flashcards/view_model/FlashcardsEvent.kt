package com.andriidubovyk.wordsnap.presentation.screens.flashcards.view_model

import com.andriidubovyk.wordsnap.domain.model.Flashcard

sealed class FlashcardsEvent {
    data class DeleteFlashcard(val flashcard: Flashcard): FlashcardsEvent()
    object RestoreFlashcard: FlashcardsEvent()
}