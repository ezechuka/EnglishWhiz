package com.javalon.englishwhiz.di

import android.content.Context
import androidx.room.Room
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.javalon.englishwhiz.data.WordModelDatabase
import com.javalon.englishwhiz.data.local.WordModelDao
import com.javalon.englishwhiz.data.repository.WordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EnglishWhizModule {

    @Provides
    @Singleton
    fun provideWordRepository(
        @ApplicationContext appContext: Context,
        objectMapper: ObjectMapper,
        wordModelDao: WordModelDao
    ): WordRepository {
        return WordRepository(appContext, objectMapper, wordModelDao)
    }

    @Provides
    @Singleton
    fun provideGson(): ObjectMapper {
        return ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @Provides
    @Singleton
    fun provideWordModelDao(wordModelDatabase: WordModelDatabase): WordModelDao {
        return wordModelDatabase.wordModelDao
    }

    @Provides
    @Singleton
    fun provideWordModelDatabase(@ApplicationContext appContext: Context): WordModelDatabase {
        return Room.databaseBuilder(appContext, WordModelDatabase::class.java, "wordModelDb")
            .build()
    }
}