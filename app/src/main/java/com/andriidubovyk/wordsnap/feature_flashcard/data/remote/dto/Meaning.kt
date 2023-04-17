package com.andriidubovyk.wordsnap.feature_flashcard.data.remote.dto

data class Meaning(
    val antonyms: List<String>,
    val definitions: List<Definition>,
    val partOfSpeech: String,
    val synonyms: List<String>
)