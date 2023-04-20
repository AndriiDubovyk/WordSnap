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
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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
        userData?.let { ud ->
            val flashcardsRef = getUserFlashcardCollectionRef(ud.userId)
            viewModelScope.launch {
                val flashcardList = flashcardUseCases.getFlashcards().first()
                if(flashcardList.isEmpty()) {
                    _actionFlow.emit(AccountAction.ShowToast("You don't have any flashcards"))
                    return@launch
                }
                flashcardsRef.clearCollection()
                flashcardsRef.addDocuments(flashcardList)
            }
        }
    }

    private fun processRestoreFlashcardsFromCloud() {
        val profileState = state.value as? AccountState.Profile ?: return
        val userData = profileState.userData
        userData?.let { ud ->
            val flashcardsRef = getUserFlashcardCollectionRef(ud.userId)
            flashcardsRef.get()
                .addOnSuccessListener { addFlashcardsFromFirebaseResult(it) }
                .addOnFailureListener {
                    viewModelScope.launch {
                        _actionFlow.emit(AccountAction.ShowToast("Can't restore flashcards"))
                    }
                }
        }
    }

    private suspend fun CollectionReference.clearCollection() {
        this.get().addOnSuccessListener { querySnapshot ->
            val batch = Firebase.firestore.batch()
            querySnapshot.documents.forEach { document ->
                batch.delete(document.reference)
            }
            batch.commit()
        }.await()
    }

    private fun addFlashcardsFromFirebaseResult(result: QuerySnapshot) {
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

    private suspend fun CollectionReference.addDocuments(flashcards: List<Flashcard>) {
        var successCount = 0
        var errorCount = 0
        for (flashcard in flashcards) {
            this.document(flashcard.id.toString()).set(flashcard)
                .addOnSuccessListener { successCount++ }
                .addOnFailureListener { errorCount++ }.await()
        }
        _actionFlow.emit(AccountAction.ShowToast(
            "Successfully saved: $successCount, errors: $errorCount"
        ))
    }

    private fun getUserFlashcardCollectionRef(userId: String): CollectionReference {
        return Firebase.firestore
            .collection("users").document(userId)
            .collection("flashcards")
    }

    private fun resetState() {
        _state.value = AccountState.SignIn()
    }

}