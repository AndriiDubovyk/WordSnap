package com.andriidubovyk.wordsnap.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.data.repository.FakeFlashcardRepository
import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.domain.utils.FlashcardOrder
import com.andriidubovyk.wordsnap.domain.utils.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
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
                    id = it,
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


    @Test
    fun `should correctly sort by word ascending`() = runBlocking {
        fillFlashcards()

        val flashcards = getFlashcards(FlashcardOrder.Word(OrderType.Ascending)).first()

        for(i in 0..flashcards.size - 2) {
            assertTrue(flashcards[i].word <= flashcards[i + 1].word)
        }
    }

    @Test
    fun `should correctly sort by word descending`() = runBlocking {
        fillFlashcards()

        val flashcards = getFlashcards(FlashcardOrder.Word(OrderType.Descending)).first()

        for(i in 0..flashcards.size - 2) {
            assertTrue(flashcards[i].word >= flashcards[i + 1].word)
        }
    }

    @Test
    fun `should correctly sort by definition ascending`() = runBlocking {
        fillFlashcards()

        val flashcards = getFlashcards(FlashcardOrder.Definition(OrderType.Ascending)).first()

        for(i in 0..flashcards.size - 2) {
            assertTrue(
                (flashcards[i].definition ?: "") <= (flashcards[i + 1].definition ?: "")
            )
        }
    }

    @Test
    fun `should correctly sort by definition descending`() = runBlocking {
        fillFlashcards()

        val flashcards = getFlashcards(FlashcardOrder.Definition(OrderType.Descending)).first()

        for(i in 0..flashcards.size - 2) {
            assertTrue(
                (flashcards[i].definition ?: "") >= (flashcards[i + 1].definition ?: "")
            )
        }
    }

    @Test
    fun `should correctly sort by translation ascending`() = runBlocking {
        fillFlashcards()

        val flashcards = getFlashcards(FlashcardOrder.Translation(OrderType.Ascending)).first()

        for(i in 0..flashcards.size - 2) {
            assertTrue(
                (flashcards[i].translation ?: "") <= (flashcards[i + 1].translation ?: "")
            )
        }
    }

    @Test
    fun `should correctly sort by translation descending`() = runBlocking {
        fillFlashcards()

        val flashcards = getFlashcards(FlashcardOrder.Translation(OrderType.Descending)).first()

        for(i in 0..flashcards.size - 2) {
            assertTrue(
                (flashcards[i].translation ?: "") >= (flashcards[i + 1].translation ?: "")
            )
        }
    }

    @Test
    fun `should correctly sort by score ascending`() = runBlocking {
        fillFlashcards()

        val flashcards = getFlashcards(FlashcardOrder.Score(OrderType.Ascending)).first()

        for(i in 0..flashcards.size - 2) {
            assertTrue(flashcards[i].score <= flashcards[i + 1].score)
        }
    }

    @Test
    fun `should correctly sort by score descending`() = runBlocking {
        fillFlashcards()

        val flashcards = getFlashcards(FlashcardOrder.Score(OrderType.Descending)).first()

        for(i in 0..flashcards.size - 2) {
            assertTrue(flashcards[i].score >= flashcards[i + 1].score)
        }
    }

    @Test
    fun `should correctly sort by date ascending`() = runBlocking {
        fillFlashcards()

        val flashcards = getFlashcards(FlashcardOrder.Date(OrderType.Ascending)).first()

        for(i in 0..flashcards.size - 2) {
            assertTrue(flashcards[i].timestamp <= flashcards[i + 1].timestamp)
        }
    }

    @Test
    fun `should correctly sort by date descending`() = runBlocking {
        fillFlashcards()

        val flashcards = getFlashcards(FlashcardOrder.Date(OrderType.Descending)).first()

        for(i in 0..flashcards.size - 2) {
            assertTrue(flashcards[i].timestamp >= flashcards[i + 1].timestamp)
        }
    }

    @Test
    fun `should search correctly`() = runBlocking {
        val goodWordFlashcard = Flashcard(id = 0, word = "abc", definition = "xax")
        val goodDefinitionFlashcard = Flashcard(id = 2, word = "x", definition = "abc")
        val goodTranslationFlashcard = Flashcard(id = 3, word = "ooo", definition = "xax", translation = "abc")
        val badFlashcard = Flashcard(id = 4, word = "qqq", definition = "uuu")
        fakeRepository.insertFlashcard(goodWordFlashcard)
        fakeRepository.insertFlashcard(goodDefinitionFlashcard)
        fakeRepository.insertFlashcard(goodTranslationFlashcard)
        fakeRepository.insertFlashcard(badFlashcard)

        val flashcards = getFlashcards(searchText = "abc").first()

        assertEquals(3, flashcards.size)
        assertTrue(flashcards.contains(goodWordFlashcard))
        assertTrue(flashcards.contains(goodDefinitionFlashcard))
        assertTrue(flashcards.contains(goodTranslationFlashcard))
        assertFalse(flashcards.contains(badFlashcard))

    }


    private fun fillFlashcards() {
        val flashcardsToInsert = mutableListOf<Flashcard>()
        val chars =  ('a'..'z')
        chars.forEachIndexed { index, c ->
            flashcardsToInsert.add(
                Flashcard(
                    id = index,
                    word = c.toString(),
                    definition = chars.random().toString(),
                    translation = chars.random().toString(),
                    score = index % 6,
                    timestamp = (0..100).random().toLong()
                )
            )
        }
        flashcardsToInsert.shuffle()
        flashcardsToInsert.forEach {
            runBlocking { fakeRepository.insertFlashcard(it) }
        }
    }
}