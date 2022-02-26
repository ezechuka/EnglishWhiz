package com.javalon.englishwhiz.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.javalon.englishwhiz.data.local.DictionaryDao
import com.javalon.englishwhiz.data.local.converters.LabelConverter
import com.javalon.englishwhiz.data.local.converters.MeaningConverter
import com.javalon.englishwhiz.data.local.converters.SynonymConverter
import com.javalon.englishwhiz.data.local.entity.DictionaryEntity

@TypeConverters(value = [MeaningConverter::class, SynonymConverter::class, LabelConverter::class])
@Database(entities = [DictionaryEntity::class], exportSchema = true, version = 2)
abstract class DictionaryDatabase: RoomDatabase() {
    abstract val dictionaryDao: DictionaryDao
}