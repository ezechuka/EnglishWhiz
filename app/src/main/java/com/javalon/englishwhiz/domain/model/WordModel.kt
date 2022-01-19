package com.javalon.englishwhiz.domain.model

import com.javalon.englishwhiz.data.local.entity.Meaning
import com.javalon.englishwhiz.data.local.entity.WordModelEntity

data class WordModel(
    val meanings: List<Meaning>?,
    val word: String,
    val wordsetId: String
) {
    fun toWordModelEntity(): WordModelEntity {
        return WordModelEntity(meanings, word, wordsetId)
    }
}