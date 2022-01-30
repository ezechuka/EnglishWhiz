package com.javalon.englishwhiz.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import com.javalon.englishwhiz.presentation.HistoryViewModel
import com.javalon.englishwhiz.ui.theme.blueText

@ExperimentalUnitApi
@Composable
fun HistoryScreen(viewModel: HistoryViewModel, onItemClick: (Int) -> Unit) {
    viewModel.getAllHistory()
    val bookmarks by remember { mutableStateOf(viewModel.history) }
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

        BookmarkList(list = bookmarks.value, onItemClick = onItemClick) {
            viewModel.deleteWordModel(it)
        }
    }
}