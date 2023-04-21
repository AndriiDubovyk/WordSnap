package com.andriidubovyk.wordsnap.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.data.repository.FakeFlashcardRepository
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DeleteAllFlashcardsTest {

    private lateinit var deleteAllFlashcards: DeleteAllFlashcards
    private lateinit var fakeRepository: FakeFlashcardRepository

    @Before
    fun setUp() {
        fakeRepository = FakeFlashcardRepository()
        deleteAllFlashcards = DeleteAllFlashcards(fakeRepository)

        val flashcardList = List(20){
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
    fun `should clear whole repository after deleting all flashcards`() = runBlocking {
        assertNotEquals(emptyList<Flashcard>(), fakeRepository.getFlashcards().first())
        deleteAllFlashcards()
        assertEquals(emptyList<Flashcard>(), fakeRepository.getFlashcards().first())
    }

}