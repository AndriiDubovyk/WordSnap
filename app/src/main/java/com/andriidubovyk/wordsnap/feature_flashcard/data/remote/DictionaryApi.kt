package com.andriidubovyk.wordsnap.feature_flashcard.data.remote

import com.andriidubovyk.wordsnap.feature_flashcard.common.DictionaryApiConstants.PARAM_WORD
import com.andriidubovyk.wordsnap.feature_flashcard.data.remote.dto.WordDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("/api/v2/entries/en/{$PARAM_WORD}")
    suspend fun getWordDetail(@Path(PARAM_WORD) word: String): WordDetailDto
}