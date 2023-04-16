package com.andriidubovyk.wordsnap.feature_flashcard.presentation.practice_flashcard

import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.Flashcard

data class PracticeFlashcardState(
    val flashcard: Flashcard? = null,
    val isAnswerVisible: Boolean = false,
    val userAnswerRating: UserAnswerRating = UserAnswerRating.NONE
)

enum class UserAnswerRating {
    NONE, BAD, OK, GOOD
}
