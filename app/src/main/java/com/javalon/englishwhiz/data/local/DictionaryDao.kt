package com.javalon.englishwhiz.data.local

import androidx.room.Dao
import androidx.room.Query
import com.javalon.englishwhiz.data.local.entity.DictionaryEntity

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM dictionary_table WHERE word LIKE :word || '%' ORDER BY word ASC LIMIT 5")
    suspend fun prefixMatch(word: String): List<DictionaryEntity>

    @Query("SELECT * FROM dictionary_table WHERE word LIKE :word")
    suspend fun search(word: String): DictionaryEntity
}