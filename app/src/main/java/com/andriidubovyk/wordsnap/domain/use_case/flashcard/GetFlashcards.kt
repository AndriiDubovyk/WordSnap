package com.andriidubovyk.wordsnap.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.domain.model.Flashcard
import com.andriidubovyk.wordsnap.domain.repository.FlashcardRepository
import kotlinx.coroutines.flow.Flow

class GetFlashcards(
    private val repository: FlashcardRepository
) {

    operator fun invoke(): Flow<List<Flashcard>> {
        return repository.getFlashcards()
    }
}