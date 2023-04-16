package com.andriidubovyk.wordsnap.feature_flashcard.presentation.flashcards

import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.Flashcard

data class FlashcardsState(
    val flashcards: List<Flashcard> = emptyList()
)