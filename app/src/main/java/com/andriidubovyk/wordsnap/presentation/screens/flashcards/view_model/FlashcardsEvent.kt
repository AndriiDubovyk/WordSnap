package com.andriidubovyk.wordsnap.presentation.screens.flashcards.view_model

import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.domain.utils.FlashcardOrder

sealed interface FlashcardsEvent {
    data class Order(val flashcardOrder: FlashcardOrder): FlashcardsEvent
    object ToggleOrderSection: FlashcardsEvent

    data class DeleteFlashcard(val flashcard: Flashcard): FlashcardsEvent
    data class ChangeSearchText(val searchText: String): FlashcardsEvent
    object ResetSearch: FlashcardsEvent
    object RestoreFlashcard: FlashcardsEvent
}