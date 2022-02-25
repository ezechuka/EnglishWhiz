package com.javalon.englishwhiz.presentation

import com.google.gson.Gson
import com.javalon.englishwhiz.data.local.entity.DictionaryEntity
import com.javalon.englishwhiz.data.local.entity.Label
import com.javalon.englishwhiz.data.local.entity.Meaning
import com.javalon.englishwhiz.domain.repository.DictionaryBaseRepository
import com.javalon.englishwhiz.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDictionaryRepository: DictionaryBaseRepository {
    override suspend fun search(word: String): List<DictionaryEntity> {
        return listOf(dictionaryEntity)
    }

    override suspend fun prefixMatch(word: String): Flow<Resource<List<DictionaryEntity>>> = flow {
        emit(Resource.Success(listOf(dictionaryEntity)))
    }
}

val meaning = Meaning("definition", "example", "speechPart", synonyms = listOf("synon", "yms"),
        labels = listOf(Label("label")))
val meaningString: String = Gson().toJson(listOf(meaning))
val dictionaryEntity = DictionaryEntity(meaningString, "word", "23dfd31")