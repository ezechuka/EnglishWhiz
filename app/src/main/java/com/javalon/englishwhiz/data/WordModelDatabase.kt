package com.javalon.englishwhiz.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.javalon.englishwhiz.data.local.WordModelDao
import com.javalon.englishwhiz.data.local.converters.LabelConverter
import com.javalon.englishwhiz.data.local.converters.MeaningConverter
import com.javalon.englishwhiz.data.local.converters.SynonymConverter
import com.javalon.englishwhiz.data.local.entity.BookmarkEntity
import com.javalon.englishwhiz.data.local.entity.HistoryEntity

@TypeConverters(value = [MeaningConverter::class, SynonymConverter::class, LabelConverter::class])
@Database(entities = [BookmarkEntity::class, HistoryEntity::class], exportSchema = true, version = 1)
abstract class WordModelDatabase: RoomDatabase() {
    abstract val wordModelDao: WordModelDao
}