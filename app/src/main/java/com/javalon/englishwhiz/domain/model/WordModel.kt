package com.javalon.englishwhiz.domain.model

import com.javalon.englishwhiz.data.local.entity.Meaning
import com.javalon.englishwhiz.data.local.entity.BookmarkEntity
import com.javalon.englishwhiz.data.local.entity.HistoryEntity

data class WordModel(
    val meanings: List<Meaning>?,
    val word: String,
    val wordsetId: String
) {
    fun toBookmarkEntity(): BookmarkEntity {
        return BookmarkEntity(meanings, word, wordsetId)
    }

    fun toHistoryEntity(): HistoryEntity {
        return HistoryEntity(meanings, word, wordsetId)
    }
}