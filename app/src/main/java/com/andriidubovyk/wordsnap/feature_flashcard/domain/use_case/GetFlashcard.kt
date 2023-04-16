package com.andriidubovyk.wordsnap.feature_flashcard.domain.use_case

import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.Flashcard
import com.andriidubovyk.wordsnap.feature_flashcard.domain.repository.FlashcardRepository

class GetFlashcard(
    private val repository: FlashcardRepository
) {

    suspend operator fun invoke(id: Int): Flashcard? {
        return repository.getFlashcardById(id)
    }
}