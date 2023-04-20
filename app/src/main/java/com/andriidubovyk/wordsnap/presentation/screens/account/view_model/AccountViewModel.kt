package com.andriidubovyk.wordsnap.presentation.screens.account.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.domain.use_case.flashcard.FlashcardUseCases
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.utils.SignInResult
import com.andriidubovyk.wordsnap.presentation.screens.account.view_model.utils.UserData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val flashcardUseCases: FlashcardUseCases
) : ViewModel() {

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
            is AccountEvent.BackupFlashcardsToCloud -> processBackupFlashcardsToCloud()
            is AccountEvent.RestoreFlashcardsFromCloud -> processRestoreFlashcardsFromCloud()
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

    private fun processBackupFlashcardsToCloud() {
        val profileState = state.value as? AccountState.Profile ?: return
        val userData = profileState.userData
        // TODO: Fix delete all previous flashcard in firebase
        userData?.let { ud ->
            val flashcardsRef = Firebase.firestore
                .collection("users").document(ud.userId)
                .collection("flashcards")
            viewModelScope.launch {
                val flashcardList = flashcardUseCases.getFlashcards().first()
                var successCount = 0
                var errorCount = 0
                for (flashcard in flashcardList) {
                    flashcardsRef.document(flashcard.id.toString()).set(flashcard)
                        .addOnSuccessListener { successCount++ }
                        .addOnFailureListener { errorCount++ }
                }
                // TODO: Fix success/error message
                _actionFlow.emit(AccountAction.ShowToast(
                    "Successfully saved: $successCount, errors: $errorCount"
                ))
            }
        }
    }

    private fun processRestoreFlashcardsFromCloud() {
        val profileState = state.value as? AccountState.Profile ?: return
        val userData = profileState.userData
        userData?.let { ud ->
            val flashcardsRef = Firebase.firestore
                .collection("users").document(ud.userId)
                .collection("flashcards")
            flashcardsRef.get()
                .addOnSuccessListener { result ->
                    viewModelScope.launch {
                        if(result.isEmpty) {
                            _actionFlow.emit(AccountAction.ShowToast(
                                "There no flashcards in the cloud"
                            ))
                            return@launch
                        }
                        var restoredFlashcardCount = 0
                        for (document in result) {
                            val flashcard = document.toObject<Flashcard>()
                            flashcardUseCases.addFlashcard(flashcard)
                            restoredFlashcardCount++
                        }
                        _actionFlow.emit(AccountAction.ShowToast(
                            "Successfully restored $restoredFlashcardCount flashcards"
                        ))
                    }
                }
                .addOnFailureListener {
                    viewModelScope.launch {
                        _actionFlow.emit(AccountAction.ShowToast(
                            "Can't restore flashcards"
                        ))
                    }
                }
        }
    }

    private fun resetState() {
        _state.value = AccountState.SignIn()
    }

}