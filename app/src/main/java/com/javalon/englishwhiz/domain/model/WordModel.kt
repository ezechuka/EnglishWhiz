package com.javalon.englishwhiz.domain.model

import android.os.Parcelable
import com.javalon.englishwhiz.data.local.entity.Meaning
import com.javalon.englishwhiz.data.local.entity.WordModelEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class WordModel(
    val meanings: List<Meaning>?,
    val word: String,
    val wordsetId: String
): Parcelable {
    fun toWordModelEntity(): WordModelEntity {
        return WordModelEntity(meanings, word, wordsetId)
    }
}