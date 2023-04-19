package com.andriidubovyk.wordsnap.data.remote

import com.andriidubovyk.wordsnap.common.DictionaryApiConstants.PARAM_WORD
import com.andriidubovyk.wordsnap.data.remote.dto.WordDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("/api/v2/entries/en/{$PARAM_WORD}")
    suspend fun getWordDetail(@Path(PARAM_WORD) word: String): WordDetailDto
}