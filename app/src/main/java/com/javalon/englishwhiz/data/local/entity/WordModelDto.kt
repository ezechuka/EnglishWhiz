package com.javalon.englishwhiz.data.local.entity


import com.google.gson.annotations.SerializedName
import com.javalon.englishwhiz.domain.model.WordModel

data class WordModelDto(
    @SerializedName("meanings")
    val meanings: List<Meaning>?,

    @SerializedName("word")
    val word: String,

    @SerializedName("wordset_id")
    val wordsetId: String
) {
//    fun toWordModel(): WordModel {
//        return WordModel(meanings, word, wordsetId)
//    }
}