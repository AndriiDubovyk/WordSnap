package com.andriidubovyk.wordsnap.di

import android.app.Application
import androidx.room.Room
import com.andriidubovyk.wordsnap.feature_flashcard.data.data_source.FlashcardDatabase
import com.andriidubovyk.wordsnap.feature_flashcard.data.repository.FlashcardRepositoryImpl
import com.andriidubovyk.wordsnap.feature_flashcard.domain.repository.FlashcardRepository
import com.andriidubovyk.wordsnap.feature_flashcard.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
            getFlashcard = GetFlashcard(repository)
        )
    }
}
