package com.javalon.englishwhiz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import com.javalon.englishwhiz.R
import com.javalon.englishwhiz.presentation.HistoryViewModel
import com.javalon.englishwhiz.ui.theme.blueText

@ExperimentalUnitApi
@Composable
fun HistoryScreen(viewModel: HistoryViewModel, onItemClick: (Int) -> Unit) {
    viewModel.getAllHistory()
    val history by remember { mutableStateOf(viewModel.history) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "History",
                style = MaterialTheme.typography.h6,
                color = blueText,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp)
                    .align(Alignment.Start)
            )

            BookmarkList(list = history.value, onItemClick = onItemClick) {
                viewModel.deleteHistory(it)
            }
        }

        if (history.value.isEmpty()) {
            Text(
                text = "History is empty. Your search history will appear here",
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.ExtraLight,
                textAlign = TextAlign.Center,
                color = blueText,
                modifier = Modifier.padding(16.dp).align(Alignment.Center)
            )
        }
    }

}