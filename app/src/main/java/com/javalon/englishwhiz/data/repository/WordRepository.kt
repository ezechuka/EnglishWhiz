package com.javalon.englishwhiz.data.repository

import com.javalon.englishwhiz.data.local.DictionaryDao
import com.javalon.englishwhiz.data.local.WordModelDao
import com.javalon.englishwhiz.data.local.entity.DictionaryEntity
import com.javalon.englishwhiz.data.local.entity.BookmarkEntity
import com.javalon.englishwhiz.data.local.entity.HistoryEntity
import com.javalon.englishwhiz.domain.model.WordModel
import com.javalon.englishwhiz.domain.repository.BaseRepository
import com.javalon.englishwhiz.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WordRepository @Inject constructor(
    private val dictionaryDao: DictionaryDao,
    private val wordModelDao: WordModelDao
) : BaseRepository {

    override suspend fun insertBookmark(bookmarkEntity: BookmarkEntity) {
        wordModelDao.insertBookmark(bookmarkEntity)
    }

    override suspend fun insertHistory(historyEntity: HistoryEntity) {
        wordModelDao.insertHistory(historyEntity)
    }

    override suspend fun getAllBookmark(): Flow<Resource<List<BookmarkEntity>>> = flow {
        emit(Resource.Loading())
        val result = wordModelDao.getAllBookmark()
        emit(Resource.Success(data = result))
    }

    override suspend fun getAllHistory(): Flow<Resource<List<HistoryEntity>>> = flow {
        emit(Resource.Loading())
        val result = wordModelDao.getAllHistory()
        emit(Resource.Success(data = result))
    }

    override suspend fun deleteBookmark(bookmarkEntity: BookmarkEntity) {
        wordModelDao.deleteBookmark(bookmarkEntity)
    }

    override suspend fun deleteHistory(historyEntity: HistoryEntity) {
        wordModelDao.deleteHistory(historyEntity)
    }

    override suspend fun prefixMatch(word: String): Flow<Resource<List<DictionaryEntity>>> = flow {
        emit(Resource.Loading())
        val matches = dictionaryDao.prefixMatch(word)
        emit(Resource.Success(data = matches))
    }

    override suspend fun search(word: String): List<DictionaryEntity> {
        return listOf(dictionaryDao.search(word))
    }
}