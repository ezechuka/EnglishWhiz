package com.javalon.englishwhiz.domain.repository

import com.javalon.englishwhiz.data.local.entity.WordModelEntity
import com.javalon.englishwhiz.domain.model.WordModel
import com.javalon.englishwhiz.util.Resource
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    fun readFromJsonStream(): Flow<Resource<MutableMap<String, List<WordModel>>>>

    suspend fun insertBookmark(wordModelEntity: WordModelEntity)

    suspend fun insertHistory(wordModelEntity: WordModelEntity)

    suspend fun getAllBookmark(): Flow<Resource<List<WordModelEntity>>>

    suspend fun getAllHistory(): Flow<Resource<List<WordModelEntity>>>

    suspend fun deleteBookmark(wordModelEntity: WordModelEntity)
}