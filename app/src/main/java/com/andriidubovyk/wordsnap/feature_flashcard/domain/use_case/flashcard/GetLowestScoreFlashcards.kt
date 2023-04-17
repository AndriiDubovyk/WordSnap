package com.andriidubovyk.wordsnap.feature_flashcard.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.Flashcard
import com.andriidubovyk.wordsnap.feature_flashcard.domain.repository.FlashcardRepository

class GetLowestScoreFlashcards(
    private val repository: FlashcardRepository
) {

    suspend operator fun invoke(): List<Flashcard> {
        return repository.getLowestScoreFlashcards()
    }
}