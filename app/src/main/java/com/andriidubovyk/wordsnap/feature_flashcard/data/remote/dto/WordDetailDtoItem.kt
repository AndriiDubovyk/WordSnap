package com.andriidubovyk.wordsnap.feature_flashcard.data.remote.dto

data class WordDetailDtoItem(
    val license: License,
    val meanings: List<Meaning>,
    val phonetics: List<Phonetic>,
    val sourceUrls: List<String>,
    val word: String
)