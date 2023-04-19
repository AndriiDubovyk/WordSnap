package com.andriidubovyk.wordsnap.presentation.screens.account.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.utils.SignInResult
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.utils.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor() : ViewModel() {

    private val _state: MutableState<AccountState> = mutableStateOf(AccountState.SignIn())
    val state: State<AccountState> = _state

    private val _actionFlow = MutableSharedFlow<AccountAction>()
    val actionFlow = _actionFlow.asSharedFlow()


    fun onEvent(event: AccountEvent) {
        when (event) {
            is AccountEvent.SignOut -> processSignOut()
            is AccountEvent.SignInClick -> processSignInClick()
            is AccountEvent.SignIn -> processSignIn(event.userData)
            is AccountEvent.GetSignInResult -> processGetSignInResult(event.signInResult)
        }
    }

    private fun processSignOut() {
        resetState()
        viewModelScope.launch {
            _actionFlow.emit(AccountAction.SignOut)
        }
    }

    private fun processSignInClick() {
        viewModelScope.launch {
            _actionFlow.emit(AccountAction.SignInClick)
        }
    }

    private fun processSignIn(userData: UserData?) {
        _state.value = AccountState.Profile(userData)
    }

    private fun processGetSignInResult(result: SignInResult) {
        if(state.value !is AccountState.SignIn) return
        _state.value = AccountState.Profile(result.data)
    }

    private fun resetState() {
        _state.value = AccountState.SignIn()
    }
}