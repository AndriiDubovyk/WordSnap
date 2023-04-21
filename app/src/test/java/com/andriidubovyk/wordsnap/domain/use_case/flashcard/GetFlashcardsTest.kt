package com.andriidubovyk.wordsnap.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.data.repository.FakeFlashcardRepository
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetFlashcardsTest {

    private lateinit var getFlashcards: GetFlashcards
    private lateinit var fakeRepository: FakeFlashcardRepository

    @Before
    fun setUp() {
        fakeRepository = FakeFlashcardRepository()
        getFlashcards = GetFlashcards(fakeRepository)
    }

    @Test
    fun `repository should return an empty list if nothing was added`() = runBlocking {
        assertEquals(emptyList<Flashcard>(), getFlashcards().first())
    }

    @Test
    fun `repository should return a list with specific note if that note was added`() = runBlocking {
        val flashcard = Flashcard(
            word = "Word",
            definition = "Definition",
            translation = "Translation"
        )
        fakeRepository.insertFlashcard(flashcard)
        assertEquals(listOf(flashcard), getFlashcards().first())
    }

    @Test
    fun `should return a list of size 100 if 100 flashcards were added`() = runBlocking {
        repeat(100) {
            fakeRepository.insertFlashcard(
                Flashcard(
                    word = it.toString(),
                    translation = it.toString(),
                    definition = it.toString(),
                    score = it,
                    timestamp = it.toLong()
                )
            )
        }
        assertEquals(100, getFlashcards().first().size)
    }
}