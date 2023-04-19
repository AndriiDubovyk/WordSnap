package com.andriidubovyk.wordsnap.presentation.screens.practice_flashcard.view_model

import com.andriidubovyk.wordsnap.domain.model.Flashcard

data class PracticeFlashcardState(
    val flashcard: Flashcard? = null,
    val isAnswerVisible: Boolean = false,
    val userAnswerRating: UserAnswerRating = UserAnswerRating.NONE
)

enum class UserAnswerRating {
    NONE, BAD, OK, GOOD
}
