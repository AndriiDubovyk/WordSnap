package com.andriidubovyk.wordsnap.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.data.repository.FakeFlashcardRepository
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class GetFlashcardTest {

    private lateinit var getFlashcard: GetFlashcard
    private lateinit var fakeRepository: FakeFlashcardRepository
    private lateinit var flashcardList: List<Flashcard>

    @Before
    fun setUp() {
        fakeRepository = FakeFlashcardRepository()
        getFlashcard = GetFlashcard(fakeRepository)

        flashcardList = List(20){
            Flashcard(
                id = it,
                word = "word$it",
                definition = "definition$it",
            )
        }
        runBlocking {
            flashcardList.forEach { fakeRepository.insertFlashcard(it) }
        }
    }

    @Test
    fun `should return the same flashcard that was inserted`() = runBlocking {
        assertEquals(flashcardList[5], getFlashcard(5))
    }

    @Test
    fun `should return null if there no flashcard with that id`() = runBlocking {
        assertEquals(null, getFlashcard(100))
    }

    @Test
    fun `should return the new flashcard that replaced the old one`() = runBlocking {
        val flashcard = Flashcard(
            id = 2,
            word = "new word",
            definition = "new definition"
        )
        fakeRepository.insertFlashcard(flashcard)
        assertEquals(flashcard, getFlashcard(2))
    }
}