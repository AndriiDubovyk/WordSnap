package com.andriidubovyk.wordsnap.feature_flashcard.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.Flashcard
import com.andriidubovyk.wordsnap.feature_flashcard.domain.repository.FlashcardRepository
import kotlinx.coroutines.flow.Flow

class GetFlashcards(
    private val repository: FlashcardRepository
) {

    operator fun invoke(): Flow<List<Flashcard>> {
        return repository.getFlashcards()
    }
}