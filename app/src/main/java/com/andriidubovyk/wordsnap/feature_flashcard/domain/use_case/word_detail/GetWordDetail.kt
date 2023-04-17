package com.andriidubovyk.wordsnap.feature_flashcard.domain.use_case.word_detail

import com.andriidubovyk.wordsnap.feature_flashcard.common.Resource
import com.andriidubovyk.wordsnap.feature_flashcard.data.remote.dto.toWordDetail
import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.WordDetail
import com.andriidubovyk.wordsnap.feature_flashcard.domain.repository.WordDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetWordDetail(
    private val repository: WordDetailRepository
) {

    operator fun invoke(word: String): Flow<Resource<WordDetail>> = flow {
        try {
            emit(Resource.Loading())
            val wordDetail = repository.getWordDetail(word).toWordDetail()
            emit(Resource.Success(wordDetail))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}