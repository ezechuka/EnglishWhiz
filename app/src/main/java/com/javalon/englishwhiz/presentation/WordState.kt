package com.javalon.englishwhiz.presentation

import com.javalon.englishwhiz.domain.model.WordModel

data class WordState(
    val wordModel: WordModel? = null,
    var isLoading: Boolean = false,
    var isContained: Boolean = false
)