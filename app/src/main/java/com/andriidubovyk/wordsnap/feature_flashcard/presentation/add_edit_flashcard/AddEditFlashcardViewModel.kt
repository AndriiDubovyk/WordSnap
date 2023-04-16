package com.andriidubovyk.wordsnap.feature_flashcard.presentation.add_edit_flashcard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.Flashcard
import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.InvalidFlashcardException
import com.andriidubovyk.wordsnap.feature_flashcard.domain.use_case.FlashcardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditFlashcardViewModel @Inject constructor(
    private val flashcardUseCases: FlashcardUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _flashcardWord = mutableStateOf(FlashcardTextFieldState(
        hint = "Enter word..."
    ))
    val flashcardWord: State<FlashcardTextFieldState> = _flashcardWord

    private val _flashcardDefinition = mutableStateOf(FlashcardTextFieldState(
        hint = "Enter definition..."
    ))
    val flashcardDefinition: State<FlashcardTextFieldState> = _flashcardDefinition

    private val _flashcardTranslation = mutableStateOf(FlashcardTextFieldState(
        hint = "Enter translation..."
    ))
    val flashcardTranslation: State<FlashcardTextFieldState> = _flashcardTranslation

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentFlashcardId: Int? = null

    init {
        savedStateHandle.get<Int>("flashcardId")?.let { flashcardId ->
            if(flashcardId != -1) {
                viewModelScope.launch {
                    flashcardUseCases.getFlashcard(flashcardId)?.also { flashcard ->
                        currentFlashcardId = flashcard.id
                        _flashcardWord.value = flashcardWord.value.copy(
                            text = flashcard.word,
                            isHintVisible = false
                        )
                        _flashcardDefinition.value = flashcardDefinition.value.copy(
                            text = flashcard.definition ?: "",
                            isHintVisible = flashcard.definition.isNullOrBlank()
                        )
                        _flashcardTranslation.value = flashcardTranslation.value.copy(
                            text = flashcard.translation ?: "",
                            isHintVisible = flashcard.translation.isNullOrBlank()
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditFlashcardEvent) {
        when(event) {
            is AddEditFlashcardEvent.EnterWord -> {
                _flashcardWord.value = flashcardWord.value.copy(
                    text = event.value
                )
            }
            is AddEditFlashcardEvent.ChangeWordFocus -> {
                _flashcardWord.value = flashcardWord.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            flashcardWord.value.text.isBlank()
                )
            }
            is AddEditFlashcardEvent.EnterDefinition -> {
                _flashcardDefinition.value = flashcardDefinition.value.copy(
                    text = event.value
                )
            }
            is AddEditFlashcardEvent.ChangeDefinitionFocus -> {
                _flashcardDefinition.value = flashcardDefinition.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            flashcardDefinition.value.text.isBlank()
                )
            }
            is AddEditFlashcardEvent.EnterTranslation -> {
                _flashcardTranslation.value = flashcardTranslation.value.copy(
                    text = event.value
                )
            }
            is AddEditFlashcardEvent.ChangeTranslationFocus -> {
                _flashcardTranslation.value = flashcardTranslation.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            flashcardTranslation.value.text.isBlank()
                )
            }
            is AddEditFlashcardEvent.SaveFlashcard -> {
                viewModelScope.launch {
                    try {
                        flashcardUseCases.addFlashcard(
                            Flashcard(
                                word = flashcardWord.value.text,
                                definition = flashcardDefinition.value.text.takeIf { it.isNotBlank() },
                                translation = flashcardTranslation.value.text.takeIf { it.isNotBlank() },
                                timestamp = System.currentTimeMillis(),
                                score = if (currentFlashcardId == -1) {
                                    0
                                } else {
                                    flashcardUseCases.getFlashcard(currentFlashcardId?:-1)?.score?:0
                                       },
                                id = currentFlashcardId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveFlashcard)
                    } catch(e: InvalidFlashcardException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save flashcard"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveFlashcard: UiEvent()
    }
}