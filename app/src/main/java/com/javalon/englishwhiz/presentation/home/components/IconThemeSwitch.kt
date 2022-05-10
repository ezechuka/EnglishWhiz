package com.javalon.englishwhiz.presentation.home.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.javalon.englishwhiz.R

@Composable
fun IconThemeSwitch(modifier: Modifier = Modifier, onToggle: () -> Unit) {
    val icon = if (isSystemInDarkTheme()) R.drawable.ic_light_off else R.drawable.ic_light_on

    IconButton(onClick = onToggle, modifier = modifier) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp, 24.dp),
            tint = colorResource(
                id = R.color.text
            )
        )
    }

}