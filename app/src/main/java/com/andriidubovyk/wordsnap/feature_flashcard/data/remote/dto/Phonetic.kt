package com.andriidubovyk.wordsnap.feature_flashcard.data.remote.dto

data class Phonetic(
    val audio: String,
    val license: License,
    val sourceUrl: String,
    val text: String
)