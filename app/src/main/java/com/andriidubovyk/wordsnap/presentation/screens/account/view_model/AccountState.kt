package com.andriidubovyk.wordsnap.presentation.screens.account.view_model

import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.utils.UserData

sealed class AccountState {
    data class SignIn(
        val isSignInSuccessful: Boolean = false,
        val signInError: String? = null
    ) : AccountState()

    data class Profile(val userData: UserData? = null) : AccountState()
}