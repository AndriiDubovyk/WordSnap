package com.andriidubovyk.wordsnap.feature_flashcard.domain.repository

import com.andriidubovyk.wordsnap.feature_flashcard.data.remote.dto.WordDetailDto

interface WordDetailRepository {

    suspend fun getWordDetail(word: String): WordDetailDto
}