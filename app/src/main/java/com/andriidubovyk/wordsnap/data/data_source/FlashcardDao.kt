package com.andriidubovyk.wordsnap.data.data_source

import androidx.room.*
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {

    @Query("SELECT * FROM flashcard")
    fun getFlashcards(): Flow<List<Flashcard>>

    @Query("SELECT * FROM flashcard WHERE id = :id")
    suspend fun getFlashcardById(id: Int): Flashcard?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcard(flashcard: Flashcard)

    @Delete
    suspend fun deleteFlashcard(flashcard: Flashcard)

    @Query("DELETE FROM flashcard")
    suspend fun deleteAllFlashcards()

    @Query("SELECT * FROM flashcard ORDER BY score ASC LIMIT 5")
    suspend fun getLowestScoreFlashcards(): List<Flashcard>

    @Query("SELECT SUM(score) FROM flashcard")
    suspend fun getTotalScore(): Int
}