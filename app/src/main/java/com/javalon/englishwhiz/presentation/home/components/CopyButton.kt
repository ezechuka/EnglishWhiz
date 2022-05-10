package com.javalon.englishwhiz.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.javalon.englishwhiz.R

@ExperimentalMaterialApi
@Composable
fun CopyButton(modifier: Modifier = Modifier, onButtonClick: () -> Unit) {

    Card(
        elevation = 0.dp,
        onClick = {
              onButtonClick()
        },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(MaterialTheme.colors.background)
                .padding(8.dp)
                .size(48.dp)
        ) {
            Icon(painterResource(id = R.drawable.copy), contentDescription = null,
                tint = MaterialTheme.colors.onBackground)
            Text(
                text = "Copy",
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}