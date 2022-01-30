package com.javalon.englishwhiz.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javalon.englishwhiz.data.local.entity.WordModelEntity

@Dao
interface WordModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordModel(wordModelEntity: WordModelEntity)

    @Query("SELECT * FROM wordModelTable WHERE isBookmark LIKE :bookmark")
    suspend fun getAllBookmark(bookmark: Boolean = true): List<WordModelEntity>

    @Query("SELECT * FROM wordModelTable WHERE isBookmark LIKE :bookmark")
    suspend fun getAllHistory(bookmark: Boolean = false): List<WordModelEntity>

    @Delete
    suspend fun deleteBookmark(wordModelEntity: WordModelEntity)
}