package com.andriidubovyk.wordsnap.presentation.screens.study.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriidubovyk.wordsnap.domain.use_case.flashcard.FlashcardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyViewModel @Inject constructor(
    private val flashcardUseCases: FlashcardUseCases
) : ViewModel() {

    private val _state = mutableStateOf(StudyState())
    val state: State<StudyState> = _state

    init {
        viewModelScope.launch {
            _state.value = state.value.copy(
                totalScore = flashcardUseCases.getTotalScore()
            )
        }
    }

}