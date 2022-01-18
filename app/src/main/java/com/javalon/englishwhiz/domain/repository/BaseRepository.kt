package com.javalon.englishwhiz.domain.repository

import com.javalon.englishwhiz.domain.model.WordModel
import com.javalon.englishwhiz.util.Resource
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    fun readFromJsonStream(): Flow<Resource<MutableMap<String, List<WordModel>>>>
}