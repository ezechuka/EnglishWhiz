package com.javalon.englishwhiz.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javalon.englishwhiz.data.local.entity.BookmarkEntity
import com.javalon.englishwhiz.data.local.entity.HistoryEntity

@Dao
interface WordModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmarkEntity: BookmarkEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(historyEntity: HistoryEntity)

    @Query("SELECT * FROM bookmarkTable")
    suspend fun getAllBookmark(): List<BookmarkEntity>

    @Query("SELECT * FROM historyTable")
    suspend fun getAllHistory(): List<HistoryEntity>

    @Delete
    suspend fun deleteBookmark(bookmarkEntity: BookmarkEntity)

    @Delete
    suspend fun deleteHistory(historyEntity: HistoryEntity)
}