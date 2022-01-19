package com.javalon.englishwhiz.data.local.entity


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.javalon.englishwhiz.domain.model.WordModel

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class WordModelDto(
    @JsonProperty("contributors")
    val contributors: List<String>?,
    @JsonProperty("editors")
    val editors: List<String>?,
    @JsonProperty("meanings")
    val meanings: List<Meaning>?,
    @JsonProperty("word")
    val word: String,
    @JsonProperty("wordset_id")
    val wordsetId: String
) {
    fun toWordModel(): WordModel {
        return WordModel(meanings, word, wordsetId)
    }
}