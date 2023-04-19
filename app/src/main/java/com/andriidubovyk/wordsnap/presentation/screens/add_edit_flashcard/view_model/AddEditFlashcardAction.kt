package com.andriidubovyk.wordsnap.presentation.screens.add_edit_flashcard.view_model

sealed class AddEditFlashcardAction {
    data class ShowSnackbar(val message: String) : AddEditFlashcardAction()
    object SaveFlashcard : AddEditFlashcardAction()
}