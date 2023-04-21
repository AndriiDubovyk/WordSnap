package com.andriidubovyk.wordsnap.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.domain.repository.FlashcardRepository
import com.andriidubovyk.wordsnap.domain.utils.FlashcardOrder
import com.andriidubovyk.wordsnap.domain.utils.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFlashcards(
    private val repository: FlashcardRepository
) {

    operator fun invoke(
        flashcardOrder: FlashcardOrder = FlashcardOrder.Date(OrderType.Descending)
    ): Flow<List<Flashcard>> {
        return repository.getFlashcards().map {
            it.sorted(flashcardOrder)
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
}