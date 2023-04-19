package com.andriidubovyk.wordsnap.di

import android.app.Application
import androidx.room.Room
import com.andriidubovyk.wordsnap.common.DictionaryApiConstants
import com.andriidubovyk.wordsnap.data.data_source.FlashcardDatabase
import com.andriidubovyk.wordsnap.data.remote.DictionaryApi
import com.andriidubovyk.wordsnap.data.repository.FlashcardRepositoryImpl
import com.andriidubovyk.wordsnap.data.repository.WordDetailRepositoryImpl
import com.andriidubovyk.wordsnap.domain.repository.FlashcardRepository
import com.andriidubovyk.wordsnap.domain.repository.WordDetailRepository
import com.andriidubovyk.wordsnap.domain.use_case.*
import com.andriidubovyk.wordsnap.domain.use_case.flashcard.*
import com.andriidubovyk.wordsnap.domain.use_case.word_detail.GetWordDetail
import com.andriidubovyk.wordsnap.domain.use_case.word_detail.WordDetailUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFlashcardDatabase(app: Application): FlashcardDatabase {
        return Room.databaseBuilder(
            app,
            FlashcardDatabase::class.java,
            FlashcardDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFlashcardRepository(db: FlashcardDatabase): FlashcardRepository {
        return FlashcardRepositoryImpl(db.flashcardDao)
    }

    @Provides
    @Singleton
    fun provideFlashcardUseCases(repository: FlashcardRepository): FlashcardUseCases {
        return FlashcardUseCases(
            getFlashcards = GetFlashcards(repository),
            deleteFlashcard = DeleteFlashcard(repository),
            addFlashcard = AddFlashcard(repository),
            getFlashcard = GetFlashcard(repository),
            getLowestScoreFlashcards = GetLowestScoreFlashcards(repository)
        )
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl(DictionaryApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDictionaryRepository(api: DictionaryApi): WordDetailRepository {
        return WordDetailRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideWordDetailUseCases(repository: WordDetailRepository): WordDetailUseCases {
        return WordDetailUseCases(
            getWordDetail = GetWordDetail(repository)
        )
    }

}
