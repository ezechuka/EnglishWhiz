package com.javalon.englishwhiz.domain.model

import com.javalon.englishwhiz.data.local.Meaning

data class WordModel(
    val meanings: List<Meaning>?,
    val word: String,
    val wordsetId: String
)