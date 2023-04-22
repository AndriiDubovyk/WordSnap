package com.andriidubovyk.wordsnap.presentation.screens.account.view_model

sealed interface AccountAction {
    object SignInClick: AccountAction
    object SignOut: AccountAction
    data class ShowToast(val message: String) : AccountAction
}
