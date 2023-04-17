package com.andriidubovyk.wordsnap.feature_flashcard.presentation.add_edit_flashcard

data class OnlineDefinitionsDialogState(
    val isOpen: Boolean = false,
    val definitions: List<String> = emptyList()
)
