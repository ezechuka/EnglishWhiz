package com.javalon.englishwhiz.data.local

import androidx.room.Dao
import androidx.room.Query
import com.javalon.englishwhiz.data.local.entity.DictionaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM dictionary_table")
    suspend fun fetchDictionaryDB(): List<DictionaryEntity>

    @Query("SELECT * FROM dictionary_table WHERE word LIKE :word || '%' LIMIT 5")
    fun prefixMatch(word: String): Flow<List<DictionaryEntity>>

    @Query("SELECT * FROM dictionary_table WHERE word LIKE :word")
    suspend fun search(word: String): DictionaryEntity
}