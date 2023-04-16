package com.andriidubovyk.wordsnap.feature_flashcard.domain.repository

import androidx.room.Query
import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.Flashcard
import kotlinx.coroutines.flow.Flow

interface FlashcardRepository {

    fun getFlashcards(): Flow<List<Flashcard>>

    suspend fun getFlashcardById(id: Int): Flashcard?

    suspend fun insertFlashcard(flashcard: Flashcard)

    suspend fun deleteFlashcard(flashcard: Flashcard)

    suspend fun getLowestScoreFlashcards(): List<Flashcard>
}