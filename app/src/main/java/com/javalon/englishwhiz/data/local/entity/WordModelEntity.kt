package com.javalon.englishwhiz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.javalon.englishwhiz.domain.model.WordModel

@Entity(tableName = "wordModelTable")
data class WordModelEntity(
    val meanings: List<Meaning>?,
    val word: String,
    @PrimaryKey
    val wordsetId: String,
    var isBookmark: Boolean = false
) {
    fun toWordModel(): WordModel {
        return WordModel(meanings, word, wordsetId)
    }
}