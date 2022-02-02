package com.javalon.englishwhiz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.javalon.englishwhiz.domain.model.WordModel

@Entity(tableName = "historyTable")
data class HistoryEntity(
    val meanings: List<Meaning>?,
    val word: String,
    @PrimaryKey
    val wordsetId: String
) {
    fun toWordModel(): WordModel {
        return WordModel(meanings, word, wordsetId)
    }
}
