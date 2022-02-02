package com.javalon.englishwhiz.data.local.entity

import com.google.gson.annotations.SerializedName

data class Label(
    @SerializedName("name")
    val name: String,
)