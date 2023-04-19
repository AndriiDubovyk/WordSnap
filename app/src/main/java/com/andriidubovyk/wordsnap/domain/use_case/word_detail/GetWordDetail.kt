package com.andriidubovyk.wordsnap.domain.use_case.word_detail

import com.andriidubovyk.wordsnap.common.Resource
import com.andriidubovyk.wordsnap.data.remote.dto.toWordDetail
import com.andriidubovyk.wordsnap.domain.model.WordDetail
import com.andriidubovyk.wordsnap.domain.repository.WordDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetWordDetail(
    private val repository: WordDetailRepository
) {

    operator fun invoke(word: String): Flow<com.andriidubovyk.wordsnap.common.Resource<WordDetail>> = flow {
        try {
            emit(com.andriidubovyk.wordsnap.common.Resource.Loading())
            val wordDetail = repository.getWordDetail(word).toWordDetail()
            emit(com.andriidubovyk.wordsnap.common.Resource.Success(wordDetail))
        } catch(e: HttpException) {
            emit(com.andriidubovyk.wordsnap.common.Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch(e: IOException) {
            emit(com.andriidubovyk.wordsnap.common.Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}