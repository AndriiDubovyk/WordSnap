package com.andriidubovyk.wordsnap.feature_flashcard.presentation.add_edit_flashcard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriidubovyk.wordsnap.feature_flashcard.common.Resource
import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.Flashcard
import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.InvalidFlashcardException
import com.andriidubovyk.wordsnap.feature_flashcard.domain.use_case.flashcard.FlashcardUseCases
import com.andriidubovyk.wordsnap.feature_flashcard.domain.use_case.word_detail.WordDetailUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditFlashcardViewModel @Inject constructor(
    private val flashcardUseCases: FlashcardUseCases,
    private val wordDetailUseCases: WordDetailUseCases,
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

    private val _onlineDefinitionsDialog = mutableStateOf(OnlineDefinitionsDialogState())
    val onlineDefinitionsDialog: State<OnlineDefinitionsDialogState> = _onlineDefinitionsDialog

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
                setText(_flashcardWord, event.value)
            }
            is AddEditFlashcardEvent.ChangeWordFocus -> {
                setFocus(_flashcardWord, event.focusState)
            }
            is AddEditFlashcardEvent.EnterDefinition -> {
                setText(_flashcardDefinition, event.value)
            }
            is AddEditFlashcardEvent.ChangeDefinitionFocus -> {
                setFocus(_flashcardDefinition, event.focusState)
            }
            is AddEditFlashcardEvent.EnterTranslation -> {
                setText(_flashcardTranslation, event.value)
            }
            is AddEditFlashcardEvent.ChangeTranslationFocus -> {
                setFocus(_flashcardTranslation, event.focusState)
            }
            is AddEditFlashcardEvent.GetDefinitionsFromDictionary -> {
                getDefinitionsFromDictionary()
            }
            is AddEditFlashcardEvent.CloseDefinitonsDialog -> {
                closeDefinitionsDialog()
            }
            is AddEditFlashcardEvent.SelectDefinitionFromDialog -> {
                selectDefinitionFromDialog(event.value)
            }
            is AddEditFlashcardEvent.SaveFlashcard -> {
                saveFlashcard()
            }
        }
    }

    private fun setText(
        fieldState: MutableState<FlashcardTextFieldState>,
        text: String
    ) {
        fieldState.value = fieldState.value.copy(
            text = text
        )
    }

    private fun setFocus(
        fieldState: MutableState<FlashcardTextFieldState>,
        focusState: FocusState
    ) {
        fieldState.value = fieldState.value.copy(
            isHintVisible = !focusState.isFocused && fieldState.value.text.isBlank()
        )
    }

    private fun getDefinitionsFromDictionary() {
        wordDetailUseCases.getWordDetail(flashcardWord.value.text).onEach { result ->
            when(result) {
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            message = "Couldn't load this word definition"
                        )
                    )
                }
                is Resource.Success -> {
                    val definitions = result.data?.definitions
                    definitions?.let {
                        _onlineDefinitionsDialog.value = onlineDefinitionsDialog.value.copy(
                            isOpen = true,
                            definitions = it
                        )
                    }
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun closeDefinitionsDialog() {
        _onlineDefinitionsDialog.value = onlineDefinitionsDialog.value.copy(
            isOpen = false,
        )
    }

    private fun selectDefinitionFromDialog(definition: String) {
        _flashcardDefinition.value = flashcardDefinition.value.copy(
            text = definition,
            isHintVisible = false
        )
        closeDefinitionsDialog()
    }

    private fun saveFlashcard() {
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
                        message = e.message ?: "Couldn't save this flashcard"
                    )
                )
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveFlashcard: UiEvent()
    }
}