package com.andriidubovyk.wordsnap.presentation.screens.add_edit_flashcard.view_model

data class OnlineDefinitionsDialogState(
    val isOpen: Boolean = false,
    val definitions: List<String> = emptyList()
)
