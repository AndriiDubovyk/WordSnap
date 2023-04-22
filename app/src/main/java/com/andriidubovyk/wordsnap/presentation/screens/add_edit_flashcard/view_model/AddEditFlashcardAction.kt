package com.andriidubovyk.wordsnap.presentation.screens.add_edit_flashcard.view_model

sealed interface AddEditFlashcardAction {
    data class ShowSnackbar(val message: String) : AddEditFlashcardAction
    object SaveFlashcard : AddEditFlashcardAction
}