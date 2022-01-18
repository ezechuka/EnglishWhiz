package com.javalon.englishwhiz.domain.model

import androidx.annotation.DrawableRes
import com.javalon.englishwhiz.R

data class UtilItem(
    val itemText: String,
    val message: String? = null,
    @DrawableRes val itemIcon: Int,
    val utilItemClick: () -> Unit = {}
)