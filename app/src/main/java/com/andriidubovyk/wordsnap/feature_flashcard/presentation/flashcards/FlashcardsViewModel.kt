package com.andriidubovyk.wordsnap.feature_flashcard.presentation.flashcards

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.Flashcard
import com.andriidubovyk.wordsnap.feature_flashcard.domain.use_case.FlashcardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashcardsViewModel @Inject constructor(
    private val flashcardUseCases: FlashcardUseCases
) : ViewModel() {

    private val _state = mutableStateOf(FlashcardsState())
    val state: State<FlashcardsState> = _state

    private var recentlyDeletedFlashcard: Flashcard? = null

    private var getFlashcardsJob: Job? = null

    init {
        getFlashcards()
    }

    fun onEvent(event: FlashcardsEvent) {
        when (event) {
            is FlashcardsEvent.DeleteFlashcard -> {
                viewModelScope.launch {
                    flashcardUseCases.deleteFlashcard(event.flashcard)
                    recentlyDeletedFlashcard = event.flashcard
                }
            }
            is FlashcardsEvent.RestoreFlashcard -> {
                viewModelScope.launch {
                    flashcardUseCases.addFlashcard(recentlyDeletedFlashcard ?: return@launch)
                    recentlyDeletedFlashcard = null
                }
            }
        }
    }

    private fun getFlashcards() {
        getFlashcardsJob?.cancel()
        getFlashcardsJob = flashcardUseCases.getFlashcards()
            .onEach { flashcards ->
                _state.value = state.value.copy(
                    flashcards = flashcards,
                )
            }
            .launchIn(viewModelScope)
    }
}