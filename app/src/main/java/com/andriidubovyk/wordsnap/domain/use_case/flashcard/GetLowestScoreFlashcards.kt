package com.andriidubovyk.wordsnap.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.domain.repository.FlashcardRepository

class GetLowestScoreFlashcards(
    private val repository: FlashcardRepository
) {

    suspend operator fun invoke(): List<Flashcard> {
        return repository.getLowestScoreFlashcards()
    }
}