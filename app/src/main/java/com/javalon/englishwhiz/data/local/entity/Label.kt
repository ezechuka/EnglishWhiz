package com.javalon.englishwhiz.data.local.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Label(
    @JsonProperty("is_dialect")
    val isDialect: Boolean,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("parent")
    val parent: Parent?
)