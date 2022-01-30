package com.javalon.englishwhiz.data.local.entity

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
@Parcelize
data class Meaning(
    @JsonProperty("def")
    val def: String,

    @JsonProperty("example")
    val example: String?,

    @JsonProperty("speech_part")
    val speechPart: String,

    @JsonProperty("synonyms")
    val synonyms: List<String>?,

    @JsonProperty("labels")
    val labels: List<Label>?,
): Parcelable