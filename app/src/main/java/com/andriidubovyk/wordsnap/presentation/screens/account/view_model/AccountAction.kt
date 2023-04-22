package com.andriidubovyk.wordsnap.presentation.screens.account.view_model

import com.andriidubovyk.wordsnap.presentation.screens.add_edit_flashcard.view_model.AddEditFlashcardAction

sealed interface AccountAction {
    object SignInClick: AccountAction
    object SignOut: AccountAction
    data class ShowToast(val message: String) : AccountAction
}
