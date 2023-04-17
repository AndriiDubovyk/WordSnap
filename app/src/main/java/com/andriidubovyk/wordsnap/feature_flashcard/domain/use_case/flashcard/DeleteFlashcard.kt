package com.andriidubovyk.wordsnap.feature_flashcard.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.Flashcard
import com.andriidubovyk.wordsnap.feature_flashcard.domain.repository.FlashcardRepository

class DeleteFlashcard(
    private val repository: FlashcardRepository
) {

    suspend operator fun invoke(flashcard: Flashcard) {
        repository.deleteFlashcard(flashcard)
    }
}