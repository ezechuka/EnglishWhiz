package com.javalon.englishwhiz.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.javalon.englishwhiz.data.local.converters.MeaningConverter
import com.javalon.englishwhiz.domain.model.WordModel

@Entity(tableName = "a_table")
data class DictionaryEntity(
    @ColumnInfo(name = "meanings")
    val meanings: String?,
    @ColumnInfo(name = "word")
    val word: String,
    @PrimaryKey
    @ColumnInfo(name = "wordsetId")
    val wordsetId: String
) {
    fun toWordModel(): WordModel {
        val type = object : TypeToken<List<Meaning>>() {}.type
        val meaningList = Gson().fromJson<List<Meaning>>(meanings, type)
        return WordModel(meaningList, word, wordsetId)
    }
}
