package com.andriidubovyk.wordsnap.presentation.screens.flashcards.view_model

import com.andriidubovyk.wordsnap.domain.model.Flashcard

data class FlashcardsState(
    val flashcards: List<Flashcard> = emptyList()
)