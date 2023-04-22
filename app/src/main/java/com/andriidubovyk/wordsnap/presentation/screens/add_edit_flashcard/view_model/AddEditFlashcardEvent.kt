package com.andriidubovyk.wordsnap.presentation.screens.add_edit_flashcard.view_model

sealed interface AddEditFlashcardEvent {
    data class EnterWord(val value: String): AddEditFlashcardEvent
    data class EnterDefinition(val value: String): AddEditFlashcardEvent
    data class EnterTranslation(val value: String): AddEditFlashcardEvent
    object GetDefinitionsFromDictionary: AddEditFlashcardEvent
    data class SelectDefinitionFromDialog(val value: String): AddEditFlashcardEvent
    object CloseDefinitionsDialog: AddEditFlashcardEvent
    object SaveFlashcard: AddEditFlashcardEvent
}