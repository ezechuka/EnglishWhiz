package com.javalon.englishwhiz.data.local.entity

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
@Parcelize
data class Label(
    @JsonProperty("name")
    val name: String,
): Parcelable