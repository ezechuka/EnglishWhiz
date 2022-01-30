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

    @Query("SELECT * FROM wordModelTable")
    suspend fun getAllBookmark(): List<WordModelEntity>

    @Delete
    suspend fun deleteBookmark(wordModelEntity: WordModelEntity)
}