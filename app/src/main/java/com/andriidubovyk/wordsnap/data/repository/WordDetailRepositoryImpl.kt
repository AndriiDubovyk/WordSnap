package com.andriidubovyk.wordsnap.data.repository

import com.andriidubovyk.wordsnap.data.remote.DictionaryApi
import com.andriidubovyk.wordsnap.data.remote.dto.WordDetailDto
import com.andriidubovyk.wordsnap.domain.repository.WordDetailRepository
import javax.inject.Inject

class WordDetailRepositoryImpl @Inject constructor(
    private val api: DictionaryApi
): WordDetailRepository {

    override suspend fun getWordDetail(word: String): WordDetailDto {
        return api.getWordDetail(word)
    }
}