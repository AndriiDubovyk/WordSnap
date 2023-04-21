package com.andriidubovyk.wordsnap.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.data.repository.FakeFlashcardRepository
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DeleteFlashcardTest {

    private lateinit var deleteFlashcard: DeleteFlashcard
    private lateinit var fakeRepository: FakeFlashcardRepository
    private lateinit var flashcardList: List<Flashcard>

    @Before
    fun setUp() {
        fakeRepository = FakeFlashcardRepository()
        deleteFlashcard = DeleteFlashcard(fakeRepository)

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
    fun `repository shouldn't contain deleted note`() = runBlocking {
        val flashcard = flashcardList[2]
        assertTrue(fakeRepository.getFlashcards().first().contains(flashcard))
        deleteFlashcard(flashcard)
        assertFalse(fakeRepository.getFlashcards().first().contains(flashcard))
    }
}