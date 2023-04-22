package com.andriidubovyk.wordsnap.presentation.screens.flashcards.view_model

import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.domain.utils.FlashcardOrder
import com.andriidubovyk.wordsnap.domain.utils.OrderType

data class FlashcardsState(
    val flashcards: List<Flashcard> = emptyList(),
    val flashcardOrder: FlashcardOrder = FlashcardOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val searchText: String = ""
)