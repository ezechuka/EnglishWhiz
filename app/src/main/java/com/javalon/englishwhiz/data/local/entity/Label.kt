package com.javalon.englishwhiz.data.local.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Label(
    @JsonProperty("name")
    val name: String,
)