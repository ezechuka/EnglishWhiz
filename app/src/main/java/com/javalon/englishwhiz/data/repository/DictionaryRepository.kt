package com.javalon.englishwhiz.data.repository

import androidx.sqlite.db.SimpleSQLiteQuery
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
    override fun prefixMatch(word: String): Flow<List<DictionaryEntity>> = flow {
        if (validateChar(word)) {
            val query = """
                SELECT * FROM ${word.first()}_table WHERE word LIKE ? ORDER BY word ASC LIMIT 20
            """
            val queryObj = SimpleSQLiteQuery(query, arrayOf("${word}%"))
            emit(dictionaryDao.prefixMatch(queryObj))
        }

    }

    override fun search(word: String): Flow<List<DictionaryEntity>> = flow {
        if (validateChar(word)) {
            val query = """
                SELECT * FROM ${word.first()}_table WHERE word = ?
            """
            val queryObj = SimpleSQLiteQuery(query, arrayOf(word))
            emit(listOf(dictionaryDao.search(queryObj)))
        }
    }
}

private fun validateChar(word: String) : Boolean {
    val firstChar = word.first().lowercase().toCharArray()[0]
    val isValidLetter = Character.isLetter(firstChar)
    val inASCII = firstChar in 'a'..'z'
    return isValidLetter && inASCII
}