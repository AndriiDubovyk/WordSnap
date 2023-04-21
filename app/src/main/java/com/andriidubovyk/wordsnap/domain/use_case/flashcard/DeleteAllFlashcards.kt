package com.andriidubovyk.wordsnap.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.domain.repository.FlashcardRepository

class DeleteAllFlashcards(
    private val repository: FlashcardRepository
) {

    suspend operator fun invoke() {
        repository.deleteAllFlashcards()
    }
}