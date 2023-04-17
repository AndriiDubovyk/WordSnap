package com.andriidubovyk.wordsnap.feature_flashcard.domain.use_case.flashcard

import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.Flashcard
import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.InvalidFlashcardException
import com.andriidubovyk.wordsnap.feature_flashcard.domain.repository.FlashcardRepository

class AddFlashcard(
    private val repository: FlashcardRepository
) {

    @Throws(InvalidFlashcardException::class)
    suspend operator fun invoke(flashcard: Flashcard) {
        if(flashcard.word.isBlank()) {
            throw InvalidFlashcardException("The word of the flashcard can't be empty.")
        }
        if(flashcard.definition.isNullOrBlank() && flashcard.translation.isNullOrBlank()) {
            throw InvalidFlashcardException("The flashcard must have a translation or definition.")
        }
        repository.insertFlashcard(flashcard)
    }
}