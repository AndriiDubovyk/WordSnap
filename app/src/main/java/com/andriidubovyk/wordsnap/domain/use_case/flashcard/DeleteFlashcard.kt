package com.andriidubovyk.wordsnap.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.domain.repository.FlashcardRepository

class DeleteFlashcard(
    private val repository: FlashcardRepository
) {

    suspend operator fun invoke(flashcard: Flashcard) {
        repository.deleteFlashcard(flashcard)
    }
}