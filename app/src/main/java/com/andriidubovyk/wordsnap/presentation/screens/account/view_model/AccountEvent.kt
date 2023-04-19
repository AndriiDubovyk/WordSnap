package com.andriidubovyk.wordsnap.presentation.screens.account.view_model

import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.utils.SignInResult
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.utils.UserData


sealed class AccountEvent {
    object SignOut: AccountEvent()
    object SignInClick: AccountEvent()
    data class SignIn(val userData: UserData?): AccountEvent()
    data class GetSignInResult(val signInResult: SignInResult): AccountEvent()
}