package com.andriidubovyk.wordsnap.domain.repository

import com.andriidubovyk.wordsnap.data.remote.dto.WordDetailDto

interface WordDetailRepository {

    suspend fun getWordDetail(word: String): WordDetailDto
}