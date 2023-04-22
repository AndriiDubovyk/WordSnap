package com.andriidubovyk.wordsnap.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.data.repository.FakeFlashcardRepository
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetTotalScoreTest {

    private lateinit var getTotalScore: GetTotalScore
    private lateinit var fakeRepository: FakeFlashcardRepository

    @Before
    fun setUp() {
        fakeRepository = FakeFlashcardRepository()
        getTotalScore = GetTotalScore(fakeRepository)
    }


    @Test
    fun `total score of empty repository is 0`() = runBlocking {
        assertEquals(0, getTotalScore())
    }

    @Test
    fun `total score of repository with 1 falshcard is score of that flashcard`() = runBlocking {
        fakeRepository.insertFlashcard(Flashcard(
            id = 1,
            word = "word",
            definition = "definition",
            score = 4
        ))
        assertEquals(4, getTotalScore())
    }

    @Test
    fun `total score is sum of all flashcards scores`() = runBlocking {
        val flashcardsToInsert = mutableListOf<Flashcard>()
        repeat(100) {
            flashcardsToInsert.add(
                Flashcard(
                    id = it,
                    word = "word",
                    definition = "definition",
                    score = (0..100).random()
                )
            )
        }
        flashcardsToInsert.forEach { fakeRepository.insertFlashcard(it) }
        assertEquals(flashcardsToInsert.sumOf { it.score }, getTotalScore())
    }
}