package com.andriidubovyk.wordsnap.domain.use_case.flashcard

data class FlashcardUseCases(
    val getFlashcards: GetFlashcards,
    val getFlashcard: GetFlashcard,
    val addFlashcard: AddFlashcard,
    val deleteFlashcard: DeleteFlashcard,
    val deleteAllFlashcards: DeleteAllFlashcards,
    val getLowestScoreFlashcards: GetLowestScoreFlashcards,
    val getTotalScore: GetTotalScore
)