package com.andriidubovyk.wordsnap.data.repository

import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.domain.repository.FlashcardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFlashcardRepository : FlashcardRepository {

    private val flashcards = mutableListOf<Flashcard>()

    override fun getFlashcards(): Flow<List<Flashcard>> {
        return flow { emit(flashcards) }
    }

    override suspend fun getFlashcardById(id: Int): Flashcard? {
        return flashcards.find { it.id == id}
    }

    override suspend fun insertFlashcard(flashcard: Flashcard) {
        flashcards.removeIf { flashcard.id == it.id } // remove previous flashcard
        flashcards.add(flashcard)
    }

    override suspend fun deleteFlashcard(flashcard: Flashcard) {
        flashcards.remove(flashcard)
    }

    override suspend fun deleteAllFlashcards() {
        flashcards.clear()
    }

    override suspend fun getLowestScoreFlashcards(): List<Flashcard> {
        return flashcards.sortedBy { it.score }.take(5)
    }

    override suspend fun getTotalScore(): Int {
        return flashcards.sumOf { it.score }
    }
}