package com.javalon.englishwhiz.di

import android.content.Context
import androidx.room.Room
import com.javalon.englishwhiz.data.DictionaryDatabase
import com.javalon.englishwhiz.data.WordModelDatabase
import com.javalon.englishwhiz.data.local.DictionaryDao
import com.javalon.englishwhiz.data.local.WordModelDao
import com.javalon.englishwhiz.data.repository.DictionaryRepository
import com.javalon.englishwhiz.data.repository.WordRepository
import com.javalon.englishwhiz.domain.repository.DictionaryBaseRepository
import com.javalon.englishwhiz.domain.repository.WordBaseRepository
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
        wordModelDao: WordModelDao
    ): WordBaseRepository {
        return WordRepository(wordModelDao)
    }

    @Provides
    @Singleton
    fun provideDictRepository(
        dictionaryDao: DictionaryDao,
    ): DictionaryBaseRepository {
        return DictionaryRepository(dictionaryDao)
    }

    @Provides
    @Singleton
    fun provideWordModelDao(wordModelDatabase: WordModelDatabase): WordModelDao {
        return wordModelDatabase.wordModelDao
    }

    @Provides
    @Singleton
    fun provideDictionaryDao(dictionaryDatabase: DictionaryDatabase): DictionaryDao {
        return dictionaryDatabase.dictionaryDao
    }

    @Provides
    @Singleton
    fun provideDictionaryDatabase(@ApplicationContext appContext: Context): DictionaryDatabase {
        return Room.databaseBuilder(appContext, DictionaryDatabase::class.java, "dictionaryDb")
            .createFromAsset("wordset/wordDB")
            .build()
    }

    @Provides
    @Singleton
    fun provideWordModelDatabase(@ApplicationContext appContext: Context): WordModelDatabase {
        return Room.databaseBuilder(appContext, WordModelDatabase::class.java, "wordModelDb")
            .build()
    }
}