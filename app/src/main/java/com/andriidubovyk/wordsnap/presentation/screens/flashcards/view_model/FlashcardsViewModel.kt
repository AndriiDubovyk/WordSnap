package com.andriidubovyk.wordsnap.presentation.screens.flashcards.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriidubovyk.wordsnap.common.LoaderHelper
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.domain.use_case.flashcard.FlashcardUseCases
import com.andriidubovyk.wordsnap.domain.utils.FlashcardOrder
import com.andriidubovyk.wordsnap.domain.utils.OrderType
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

    companion object {
        val DEFAULT_ORDER = FlashcardOrder.Date(OrderType.Descending)
    }

    init {
        getFlashcards(DEFAULT_ORDER)
    }

    fun onEvent(event: FlashcardsEvent) {
        when (event) {
            is FlashcardsEvent.DeleteFlashcard -> processDeleteFlashcard(event.flashcard)
            is FlashcardsEvent.RestoreFlashcard -> processRestoreFlashcard()
            is FlashcardsEvent.Order -> processOrder(event.flashcardOrder)
            is FlashcardsEvent.ToggleOrderSection -> processToggleOrderSection()
            is FlashcardsEvent.ChangeSearchText -> processChangeSearchText(event.searchText)
            is FlashcardsEvent.ResetSearch -> processResetSearch()
        }
    }

    private fun getFlashcards(
        order: FlashcardOrder,
        searchText: String = ""
    ) {
        getFlashcardsJob?.cancel()
        getFlashcardsJob = flashcardUseCases.getFlashcards(order, searchText)
            .onEach { flashcards ->
                _state.value = state.value.copy(
                    flashcards = flashcards,
                    flashcardOrder = order
                )
                LoaderHelper.isLoading = false
            }
            .launchIn(viewModelScope)
    }

    private fun processDeleteFlashcard(flashcard: Flashcard) {
        viewModelScope.launch {
            flashcardUseCases.deleteFlashcard(flashcard)
            recentlyDeletedFlashcard = flashcard
        }
    }

    private fun processChangeSearchText(searchText: String) {
        setSearch(searchText)
    }

    private fun processResetSearch() {
        setSearch("")
    }

    private fun setSearch(searchText: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                searchText = searchText
            )
            getFlashcards(
                order = state.value.flashcardOrder,
                searchText = searchText
            )
        }
    }

    private fun processRestoreFlashcard() {
        viewModelScope.launch {
            flashcardUseCases.addFlashcard(recentlyDeletedFlashcard ?: return@launch)
            recentlyDeletedFlashcard = null
        }
    }

    private fun processOrder(order: FlashcardOrder) {
        if (state.value.flashcards::class == order::class &&
            state.value.flashcardOrder.orderType == order.orderType) {
            return // if we have same order we must change nothing
        }
        getFlashcards(order)
    }

    private fun processToggleOrderSection() {
        _state.value = state.value.copy(
            isOrderSectionVisible = !state.value.isOrderSectionVisible
        )
    }
}