package com.javalon.englishwhiz.di

import android.content.Context
import com.google.gson.Gson
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
    fun provideWordRepository(@ApplicationContext appContext: Context, gson: Gson): WordRepository {
        return WordRepository(appContext, gson)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}