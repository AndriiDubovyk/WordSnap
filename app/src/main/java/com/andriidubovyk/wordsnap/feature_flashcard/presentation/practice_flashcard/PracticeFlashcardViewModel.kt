package com.andriidubovyk.wordsnap.feature_flashcard.presentation.practice_flashcard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriidubovyk.wordsnap.feature_flashcard.domain.use_case.flashcard.FlashcardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeFlashcardViewModel @Inject constructor(
    private val flashcardUseCases: FlashcardUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(PracticeFlashcardState())
    val state: State<PracticeFlashcardState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("flashcardId")?.let { flashcardId ->
            viewModelScope.launch {
                if (flashcardId != -1) {
                    _state.value = state.value.copy(
                        flashcard = flashcardUseCases.getFlashcard(flashcardId)
                    )
                } else {
                    val flashcardsToStudy = flashcardUseCases.getLowestScoreFlashcards()
                    if (flashcardsToStudy.isEmpty()) return@launch
                    _state.value = state.value.copy(
                        flashcard = flashcardsToStudy.shuffled()[0] // get random
                    )
                }
            }
        }
    }

    fun onEvent(event: PracticeFlashcardEvent) {
        when (event) {
            is PracticeFlashcardEvent.ShowAnswer -> {
                _state.value = state.value.copy(
                    isAnswerVisible = true
                )
            }
            is PracticeFlashcardEvent.SelectAnswerRating -> {
                _state.value = state.value.copy(
                    userAnswerRating = event.value
                )
                // Update score according to the answer
                state.value.flashcard?.let {
                    viewModelScope.launch {
                        flashcardUseCases.addFlashcard(
                            flashcard = it.copy(
                                score = getNewFlashcardScoreByAnswer(
                                    prevScore = it.score,
                                    answer = event.value
                                )
                            )
                        )
                    }
                }
            }
            is PracticeFlashcardEvent.GoToNext -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.GoToNextFlashcardPractice)
                }
            }
        }
    }


    private fun getNewFlashcardScoreByAnswer(
        prevScore: Int,
        answer: UserAnswerRating
    ): Int {
        return when(answer) {
            UserAnswerRating.BAD -> if (prevScore < 1) 0 else prevScore - 1
            UserAnswerRating.GOOD -> prevScore + 1
            else -> prevScore
        }
    }

    sealed class UiEvent {
        object GoToNextFlashcardPractice: UiEvent()
    }

}