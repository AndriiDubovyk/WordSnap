package com.andriidubovyk.wordsnap.feature_flashcard.presentation.add_edit_flashcard

import androidx.compose.ui.focus.FocusState

sealed class AddEditFlashcardEvent{
    data class EnterWord(val value: String): AddEditFlashcardEvent()
    data class ChangeWordFocus(val focusState: FocusState): AddEditFlashcardEvent()
    data class EnterDefinition(val value: String): AddEditFlashcardEvent()
    data class ChangeDefinitionFocus(val focusState: FocusState): AddEditFlashcardEvent()
    data class EnterTranslation(val value: String): AddEditFlashcardEvent()
    data class ChangeTranslationFocus(val focusState: FocusState): AddEditFlashcardEvent()
    object SaveFlashcard: AddEditFlashcardEvent()
}