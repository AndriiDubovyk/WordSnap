package com.andriidubovyk.wordsnap.feature_flashcard.data.remote.dto

import com.andriidubovyk.wordsnap.feature_flashcard.domain.model.WordDetail

class WordDetailDto : ArrayList<WordDetailDtoItem>()

fun WordDetailDto.toWordDetail(): WordDetail {
    if(this.isEmpty()) throw Exception("Can't get this word with Dictionary API")
    return WordDetail(
        word = this[0].word,
        definitions = this[0].meanings.flatMap { meaning ->
            meaning.definitions.map { it.definition }
        }
    )
}