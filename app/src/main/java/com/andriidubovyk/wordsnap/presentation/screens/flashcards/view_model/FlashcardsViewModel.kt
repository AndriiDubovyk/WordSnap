package com.andriidubovyk.wordsnap.presentation.screens.flashcards.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.domain.use_case.flashcard.FlashcardUseCases
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
        initFlashcards()
    }

    fun onEvent(event: FlashcardsEvent) {
        when (event) {
            is FlashcardsEvent.DeleteFlashcard -> processDeleteFlashcard(event.flashcard)
            is FlashcardsEvent.RestoreFlashcard -> processRestoreFlashcard()
        }
    }

    private fun initFlashcards() {
        getFlashcardsJob?.cancel()
        getFlashcardsJob = flashcardUseCases.getFlashcards()
            .onEach { flashcards ->
                _state.value = state.value.copy(
                    flashcards = flashcards,
                )
            }
            .launchIn(viewModelScope)
    }

    private fun processDeleteFlashcard(flashcard: Flashcard) {
        viewModelScope.launch {
            flashcardUseCases.deleteFlashcard(flashcard)
            recentlyDeletedFlashcard = flashcard
        }
    }

    private fun processRestoreFlashcard() {
        viewModelScope.launch {
            flashcardUseCases.addFlashcard(recentlyDeletedFlashcard ?: return@launch)
            recentlyDeletedFlashcard = null
        }
    }
}