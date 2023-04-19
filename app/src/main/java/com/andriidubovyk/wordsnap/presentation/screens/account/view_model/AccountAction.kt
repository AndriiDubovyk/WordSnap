package com.andriidubovyk.wordsnap.presentation.screens.account.view_model

sealed class AccountAction {
    object SignInClick: AccountAction()
    object SignOut: AccountAction()
}
