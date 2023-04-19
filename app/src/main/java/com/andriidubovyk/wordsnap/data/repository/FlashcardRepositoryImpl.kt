package com.andriidubovyk.wordsnap.data.repository

import com.andriidubovyk.wordsnap.data.data_source.FlashcardDao
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.domain.repository.FlashcardRepository
import kotlinx.coroutines.flow.Flow

class FlashcardRepositoryImpl(
    private val dao: FlashcardDao
) : FlashcardRepository {

    override fun getFlashcards(): Flow<List<Flashcard>> {
        return dao.getFlashcards()
    }

    override suspend fun getFlashcardById(id: Int): Flashcard? {
        return dao.getFlashcardById(id)
    }

    override suspend fun insertFlashcard(flashcard: Flashcard) {
        dao.insertFlashcard(flashcard)
    }

    override suspend fun deleteFlashcard(flashcard: Flashcard) {
        dao.deleteFlashcard(flashcard)
    }

    override suspend fun getLowestScoreFlashcards(): List<Flashcard> {
        return dao.getLowestScoreFlashcards()
    }
}