package com.andriidubovyk.wordsnap.feature_flashcard.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Flashcard(
    val word: String,
    val definition: String?,
    val translation: String?,
    val score: Int = 0,
    val timestamp: Long,
    @PrimaryKey val id: Int? = null
)

class InvalidFlashcardException(message: String): Exception(message)