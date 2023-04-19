package com.andriidubovyk.wordsnap.presentation.screens.add_edit_flashcard.view_model

import androidx.compose.ui.focus.FocusState

sealed class AddEditFlashcardEvent{
    data class EnterWord(val value: String): AddEditFlashcardEvent()
    data class ChangeWordFocus(val focusState: FocusState): AddEditFlashcardEvent()
    data class EnterDefinition(val value: String): AddEditFlashcardEvent()
    data class ChangeDefinitionFocus(val focusState: FocusState): AddEditFlashcardEvent()
    data class EnterTranslation(val value: String): AddEditFlashcardEvent()
    data class ChangeTranslationFocus(val focusState: FocusState): AddEditFlashcardEvent()
    object GetDefinitionsFromDictionary: AddEditFlashcardEvent()
    data class SelectDefinitionFromDialog(val value: String): AddEditFlashcardEvent()
    object CloseDefinitonsDialog: AddEditFlashcardEvent()
    object SaveFlashcard: AddEditFlashcardEvent()
}