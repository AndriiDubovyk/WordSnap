package com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriidubovyk.wordsnap.domain.use_case.flashcard.FlashcardUseCases
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

    private val _actionFlow = MutableSharedFlow<PracticeFlashcardAction>()
    val actionFlow = _actionFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("flashcardId")?.let {
            initFlashcard(it)
        }
    }

    fun onEvent(event: PracticeFlashcardEvent) {
        when (event) {
            is PracticeFlashcardEvent.ShowAnswer -> processShowAnswer()
            is PracticeFlashcardEvent.SelectAnswerRating -> processSelectAnswerRating(event.value)
            is PracticeFlashcardEvent.GoToNext -> processGoToNext()
        }
    }

    private fun initFlashcard(id: Int) {
        viewModelScope.launch {
            if (id != -1) {
                _state.value = state.value.copy(
                    flashcard = flashcardUseCases.getFlashcard(id)
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

    private fun processShowAnswer() {
        _state.value = state.value.copy(
            isAnswerVisible = true
        )
    }

    private fun processGoToNext() {
        viewModelScope.launch {
            _actionFlow.emit(PracticeFlashcardAction.GoToNextFlashcardPractice)
        }
    }

    private fun processSelectAnswerRating(rating: UserAnswerRating) {
        _state.value = state.value.copy(
            userAnswerRating = rating
        )
        // Update score according to the answer
        state.value.flashcard?.let {
            viewModelScope.launch {
                flashcardUseCases.addFlashcard(
                    flashcard = it.copy(
                        score = getNewFlashcardScoreByAnswer(
                            prevScore = it.score,
                            answerRating = rating
                        )
                    )
                )
            }
        }
    }


    private fun getNewFlashcardScoreByAnswer(
        prevScore: Int,
        answerRating: UserAnswerRating
    ): Int {
        return when(answerRating) {
            UserAnswerRating.BAD -> if (prevScore < 1) 0 else prevScore - 1
            UserAnswerRating.GOOD -> prevScore + 1
            else -> prevScore
        }
    }
}