package com.javalon.englishwhiz.domain.repository

import com.javalon.englishwhiz.data.local.entity.DictionaryEntity
import com.javalon.englishwhiz.util.Resource
import kotlinx.coroutines.flow.Flow

interface DictionaryBaseRepository {
    suspend fun search(word: String): List<DictionaryEntity>

    suspend fun prefixMatch(word: String): Flow<Resource<List<DictionaryEntity>>>
}