package com.javalon.englishwhiz.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.javalon.englishwhiz.R
import com.javalon.englishwhiz.domain.model.WordModel
import com.javalon.englishwhiz.presentation.BookmarkViewModel
import com.javalon.englishwhiz.ui.theme.blueText
import com.javalon.englishwhiz.ui.theme.cardBGDay

@ExperimentalUnitApi
@Composable
fun BookmarkScreen(viewModel: BookmarkViewModel, onItemClick: (Int) -> Unit) {
    viewModel.getAllBookmark()
    val bookmarks by remember { mutableStateOf(viewModel.bookmarks) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Bookmarks",
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

@ExperimentalUnitApi
@Composable
fun BookmarkList(list: List<WordModel>, onItemClick: (Int) -> Unit, onDeleteClick: (WordModel) -> Unit) {
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            itemsIndexed(list) { index, item ->
                BookmarkItem(index, wordModel = item, onItemClick, onDeleteClick)
            }
        }
}

@ExperimentalUnitApi
@Composable
fun BookmarkItem(index: Int, wordModel: WordModel, onItemClick: (Int) -> Unit, onDeleteClick: (WordModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .height(96.dp)
            .clickable {
                onItemClick(index)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp,
        backgroundColor = cardBGDay
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(4f)
            ) {
                Text(
                    text = wordModel.word,
                    style = MaterialTheme.typography.subtitle1,
                    color = blueText,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "1. ${wordModel.meanings?.get(0)?.def}",
                    style = MaterialTheme.typography.subtitle2,
                    color = blueText,
                    maxLines = 2,
                    lineHeight = TextUnit(16f, TextUnitType.Sp),
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                )
                wordModel.meanings?.get(0)?.synonyms?.let {
                    Text(
                        text = "Synonym(s): ${
                            it.toString().removePrefix("[")
                                .removeSuffix("]")
                        }",
                        style = MaterialTheme.typography.subtitle2,
                        color = blueText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = TextUnit(14f, TextUnitType.Sp),
                        textAlign = TextAlign.Start,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Normal,
                    )
                }
                wordModel.meanings?.get(0)?.example?.let {
                    Text(
                        text = "Ex: $it",
                        style = MaterialTheme.typography.subtitle2,
                        color = blueText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = TextUnit(14f, TextUnitType.Sp),
                        textAlign = TextAlign.Start,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }

            IconButton(onClick = { onDeleteClick(wordModel) }, modifier = Modifier.weight(.5f)) {
                Icon(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }
    }
}