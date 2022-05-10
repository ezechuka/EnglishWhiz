package com.javalon.englishwhiz.presentation.bookmark.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
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
import com.javalon.englishwhiz.presentation.ui.theme.blueText

@ExperimentalUnitApi
@Composable
fun BookmarkItem(
    index: Int,
    wordModel: WordModel,
    onItemClick: (Int) -> Unit,
    onDeleteClick: (WordModel) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .wrapContentHeight()
            .clickable {
                onItemClick(index)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.surface)
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
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "1. ${wordModel.meanings?.get(0)?.def}",
                    style = MaterialTheme.typography.subtitle2,
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