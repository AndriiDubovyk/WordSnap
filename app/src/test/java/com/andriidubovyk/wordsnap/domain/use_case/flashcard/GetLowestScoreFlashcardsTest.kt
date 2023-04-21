package com.andriidubovyk.wordsnap.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.data.repository.FakeFlashcardRepository
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetLowestScoreFlashcardsTest{

    private lateinit var getLowestScoreFlashcards: GetLowestScoreFlashcards
    private lateinit var fakeRepository: FakeFlashcardRepository

    @Before
    fun setUp() {
        fakeRepository = FakeFlashcardRepository()
        getLowestScoreFlashcards = GetLowestScoreFlashcards(fakeRepository)
    }

    @Test
    fun `should return 5 flashcards with lowest score and ordered by score`() = runBlocking {
        val flashcardList = MutableList(20){
            Flashcard(
                id = it,
                word = "word$it",
                definition = "definition$it",
                score = it
            )
        }
        flashcardList.shuffle()
        flashcardList.forEach { fakeRepository.insertFlashcard(it) }

        val lsFlashcards = getLowestScoreFlashcards()

        assertEquals(5, lsFlashcards.size)
        for(i in 0..lsFlashcards.size - 2) {
            assertTrue(lsFlashcards[i].score < lsFlashcards[i+1].score)
        }
    }

    @Test
    fun `should return 2 flashcards if there only 2`() = runBlocking {
        val flashcardList = MutableList(2){
            Flashcard(
                id = it,
                word = "word$it",
                definition = "definition$it",
                score = 5 - it
            )
        }
        flashcardList.forEach { fakeRepository.insertFlashcard(it) }

        val lsFlashcards = getLowestScoreFlashcards()

        assertEquals(2, lsFlashcards.size)
        for(flashcard in flashcardList) {
            assertTrue(lsFlashcards.contains(flashcard))
        }

        for(i in 0..lsFlashcards.size - 2) {
            assertTrue(lsFlashcards[i].score < lsFlashcards[i+1].score)
        }
    }

    @Test
    fun `should return an empty list if there no flashcard`() = runBlocking {
        val lsFlashcards = getLowestScoreFlashcards()
        assertTrue(lsFlashcards.isEmpty())
    }

}