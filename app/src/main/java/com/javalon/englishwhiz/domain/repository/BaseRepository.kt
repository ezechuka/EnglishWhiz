package com.javalon.englishwhiz.domain.repository

import com.javalon.englishwhiz.data.local.entity.DictionaryEntity
import com.javalon.englishwhiz.data.local.entity.BookmarkEntity
import com.javalon.englishwhiz.data.local.entity.HistoryEntity
import com.javalon.englishwhiz.domain.model.WordModel
import com.javalon.englishwhiz.util.Resource
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    fun readFromDB(): Flow<Resource<List<WordModel>>>

    suspend fun insertBookmark(bookmarkEntity: BookmarkEntity)

    suspend fun insertHistory(historyEntity: HistoryEntity)

    suspend fun getAllBookmark(): Flow<Resource<List<BookmarkEntity>>>

    suspend fun getAllHistory(): Flow<Resource<List<HistoryEntity>>>

    suspend fun deleteBookmark(bookmarkEntity: BookmarkEntity)

    suspend fun deleteHistory(historyEntity: HistoryEntity)

    suspend fun search(word: String): List<DictionaryEntity>

    fun prefixMatch(word: String): Flow<List<DictionaryEntity>>

}