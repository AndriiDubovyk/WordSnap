package com.andriidubovyk.wordsnap.presentation.screens.add_edit_flashcard.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriidubovyk.wordsnap.common.Resource
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.domain.model.InvalidFlashcardException
import com.andriidubovyk.wordsnap.domain.use_case.flashcard.FlashcardUseCases
import com.andriidubovyk.wordsnap.domain.use_case.word_detail.WordDetailUseCases
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

    private val _flashcardWord = mutableStateOf("")
    val flashcardWord: State<String> = _flashcardWord

    private val _flashcardDefinition = mutableStateOf("")
    val flashcardDefinition: State<String> = _flashcardDefinition

    private val _flashcardTranslation = mutableStateOf("")
    val flashcardTranslation: State<String> = _flashcardTranslation

    private val _onlineDefinitionsDialog = mutableStateOf(OnlineDefinitionsDialogState())
    val onlineDefinitionsDialog: State<OnlineDefinitionsDialogState> = _onlineDefinitionsDialog

    private val _actionFlow = MutableSharedFlow<AddEditFlashcardAction>()
    val actionFlow = _actionFlow.asSharedFlow()

    private var currentFlashcardId: Int? = null

    init {
        savedStateHandle.get<Int>("flashcardId")?.let {
            initFlashcard(it)
        }
    }

    fun onEvent(event: AddEditFlashcardEvent) {
        when (event) {
            is AddEditFlashcardEvent.EnterWord -> processEnterWord(event.value)
            is AddEditFlashcardEvent.EnterDefinition -> processEnterDefinition(event.value)
            is AddEditFlashcardEvent.EnterTranslation -> processEnterTranslation(event.value)
            is AddEditFlashcardEvent.GetDefinitionsFromDictionary -> processGetDefinitionsFromDictionary()
            is AddEditFlashcardEvent.CloseDefinitionsDialog -> processCloseDefinitionsDialog()
            is AddEditFlashcardEvent.SelectDefinitionFromDialog -> processSelectDefinitionFromDialog(event.value)
            is AddEditFlashcardEvent.SaveFlashcard -> processSaveFlashcard()
        }
    }

    private fun initFlashcard(id: Int) {
        if (id == -1) return
        viewModelScope.launch {
            flashcardUseCases.getFlashcard(id)?.also { flashcard ->
                currentFlashcardId = flashcard.id
                _flashcardWord.value = flashcard.word
                _flashcardDefinition.value = flashcard.definition ?: ""
                _flashcardTranslation.value = flashcard.translation ?: ""
            }
        }
    }

    private fun processEnterWord(value: String) {
        setText(_flashcardWord, value)
    }

    private fun processEnterDefinition(value: String) {
        setText(_flashcardDefinition, value)
    }

    private fun processEnterTranslation(value: String) {
        setText(_flashcardTranslation, value)
    }

    private fun processGetDefinitionsFromDictionary() {
        wordDetailUseCases.getWordDetail(flashcardWord.value).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _actionFlow.emit(
                        AddEditFlashcardAction.ShowSnackbar(
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

    private fun processCloseDefinitionsDialog() {
        _onlineDefinitionsDialog.value = onlineDefinitionsDialog.value.copy(
            isOpen = false,
        )
    }

    private fun processSelectDefinitionFromDialog(definition: String) {
        _flashcardDefinition.value = definition
        processCloseDefinitionsDialog()
    }

    private fun processSaveFlashcard() {
        viewModelScope.launch {
            try {
                flashcardUseCases.addFlashcard(
                    Flashcard(
                        id = currentFlashcardId,
                        word = flashcardWord.value,
                        definition = flashcardDefinition.value.takeIf { it.isNotBlank() },
                        translation = flashcardTranslation.value.takeIf { it.isNotBlank() },
                        timestamp = System.currentTimeMillis(),
                        score = if (currentFlashcardId == -1) {
                            0
                        } else {
                            flashcardUseCases.getFlashcard(currentFlashcardId ?: -1)?.score ?: 0
                        }
                    )
                )
                _actionFlow.emit(AddEditFlashcardAction.SaveFlashcard)
            } catch (e: InvalidFlashcardException) {
                _actionFlow.emit(
                    AddEditFlashcardAction.ShowSnackbar(
                        message = e.message ?: "Couldn't save this flashcard"
                    )
                )
            }
        }
    }

    private fun setText(
        fieldState: MutableState<String>,
        text: String
    ) {
        fieldState.value = text
    }
}