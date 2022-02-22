package com.javalon.englishwhiz.data.repository

import com.javalon.englishwhiz.data.local.DictionaryDao
import com.javalon.englishwhiz.data.local.entity.DictionaryEntity
import com.javalon.englishwhiz.domain.repository.DictionaryBaseRepository
import com.javalon.englishwhiz.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DictionaryRepository @Inject constructor(
    private val dictionaryDao: DictionaryDao,
) : DictionaryBaseRepository {
    override suspend fun prefixMatch(word: String): Flow<Resource<List<DictionaryEntity>>> = flow {
        emit(Resource.Loading())
        val matches = dictionaryDao.prefixMatch(word)
        emit(Resource.Success(data = matches))
    }

    override suspend fun search(word: String): List<DictionaryEntity> {
        return listOf(dictionaryDao.search(word))
    }
}