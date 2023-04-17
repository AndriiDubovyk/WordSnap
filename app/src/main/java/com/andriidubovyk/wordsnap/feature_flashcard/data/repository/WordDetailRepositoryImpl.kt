package com.andriidubovyk.wordsnap.feature_flashcard.data.repository

import android.util.Log
import com.andriidubovyk.wordsnap.feature_flashcard.data.remote.DictionaryApi
import com.andriidubovyk.wordsnap.feature_flashcard.data.remote.dto.WordDetailDto
import com.andriidubovyk.wordsnap.feature_flashcard.domain.repository.WordDetailRepository
import javax.inject.Inject

class WordDetailRepositoryImpl @Inject constructor(
    private val api: DictionaryApi
): WordDetailRepository {

    override suspend fun getWordDetail(word: String): WordDetailDto {
        return api.getWordDetail(word)
    }
}