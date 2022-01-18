package com.javalon.englishwhiz.data.local

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Meaning(
    @JsonProperty("def")
    val def: String,
    @JsonProperty("example")
    val example: String?,
    @JsonProperty("id")
    val id: String,
    @JsonProperty("speech_part")
    val speechPart: String,
    @JsonProperty("synonyms")
    val synonyms: List<String>?,
    @JsonProperty("labels")
    val labels: List<Label>?,
)