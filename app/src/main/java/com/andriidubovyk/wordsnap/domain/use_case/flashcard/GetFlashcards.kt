package com.andriidubovyk.wordsnap.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.domain.repository.FlashcardRepository
import com.andriidubovyk.wordsnap.domain.utils.FlashcardOrder
import com.andriidubovyk.wordsnap.domain.utils.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class GetFlashcards(
    private val repository: FlashcardRepository
) {

    operator fun invoke(
        flashcardOrder: FlashcardOrder = FlashcardOrder.Date(OrderType.Descending),
        searchText: String = ""
    ): Flow<List<Flashcard>> {
        return repository.getFlashcards().map {
            it.sorted(flashcardOrder).searched(searchText)
        }
    }

    private fun List<Flashcard>.sorted(
        flashcardOrder: FlashcardOrder
    ): List<Flashcard> {
        return when(flashcardOrder.orderType) {
            is OrderType.Ascending -> {
                when(flashcardOrder) {
                    is FlashcardOrder.Word -> this.sortedBy { it.word.lowercase() }
                    is FlashcardOrder.Definition -> this.sortedBy { it.definition?.lowercase() }
                    is FlashcardOrder.Translation -> this.sortedBy { it.translation?.lowercase() }
                    is FlashcardOrder.Score -> this.sortedBy { it.score }
                    is FlashcardOrder.Date -> this.sortedBy { it.timestamp }
                }
            }
            is OrderType.Descending -> {
                when(flashcardOrder) {
                    is FlashcardOrder.Word -> this.sortedByDescending { it.word.lowercase() }
                    is FlashcardOrder.Definition -> this.sortedByDescending { it.definition?.lowercase() }
                    is FlashcardOrder.Translation -> this.sortedByDescending { it.translation?.lowercase() }
                    is FlashcardOrder.Score -> this.sortedByDescending { it.score }
                    is FlashcardOrder.Date -> this.sortedByDescending { it.timestamp }
                }
            }
        }
    }

    private fun List<Flashcard>.searched(
        searchText: String
    ): List<Flashcard> {
        if (searchText.isBlank()) return this
        return this.filter { it.matches(searchText) }
    }

    private fun Flashcard.matches(searchText: String): Boolean {
        return this.word.contains(searchText)
                || this.definition?.contains(searchText)?: false
                || this.translation?.contains(searchText)?:false
    }
}