package com.andriidubovyk.wordsnap.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.data.repository.FakeFlashcardRepository
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.domain.model.InvalidFlashcardException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AddFlashcardTest{

    private lateinit var addFlashcard: AddFlashcard
    private lateinit var fakeRepository: FakeFlashcardRepository

    @Before
    fun setUp() {
        fakeRepository = FakeFlashcardRepository()
        addFlashcard = AddFlashcard(fakeRepository)
    }


    @Test
    fun `should throw InvalidFlashcardException when word is blank`() {
        val flashcard = Flashcard(
            word = "",
            definition = "definition",
            translation = "translation"
        )
        assertThrows(InvalidFlashcardException::class.java) {
            runBlocking { addFlashcard(flashcard) }
        }
    }

    @Test
    fun `should throw InvalidFlashcardException when definition and translation are blank`() {
        val flashcard = Flashcard(
            word = "word",
            definition = "",
            translation = ""
        )
        assertThrows(InvalidFlashcardException::class.java) {
            runBlocking { addFlashcard(flashcard) }
        }
    }

    @Test
    fun `should throw InvalidFlashcardException when definition and translation are null`() {
        val flashcard = Flashcard(
            word = "word",
            definition = null,
            translation = null
        )
        assertThrows(InvalidFlashcardException::class.java) {
            runBlocking { addFlashcard(flashcard) }
        }
    }

    @Test
    fun `should add note without exception if there at least definition`() {
        val flashcard = Flashcard(
            word = "word",
            definition = "definition",
            translation = ""
        )
        runBlocking { addFlashcard(flashcard) }
    }

    @Test
    fun `should add note without exception if there at least translation`() {
        val flashcard = Flashcard(
            word = "word",
            definition = "",
            translation = "translation"
        )
        runBlocking { addFlashcard(flashcard) }
    }

    @Test
    fun `should add note without exception if there definition and translation`() {
        val flashcard = Flashcard(
            word = "word",
            definition = "definition",
            translation = "translation"
        )
        runBlocking { addFlashcard(flashcard) }
    }
}