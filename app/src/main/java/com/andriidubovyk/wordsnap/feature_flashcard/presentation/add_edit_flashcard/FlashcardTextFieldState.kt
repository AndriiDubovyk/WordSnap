package com.andriidubovyk.wordsnap.feature_flashcard.presentation.add_edit_flashcard

data class FlashcardTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
