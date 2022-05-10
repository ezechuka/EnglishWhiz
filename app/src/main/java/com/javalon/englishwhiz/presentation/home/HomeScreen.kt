package com.javalon.englishwhiz.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.javalon.englishwhiz.domain.model.WordModel
import com.javalon.englishwhiz.presentation.bookmark.BookmarkViewModel
import com.javalon.englishwhiz.presentation.history.HistoryViewModel
import com.javalon.englishwhiz.presentation.home.components.AutoCompleteTextField
import com.javalon.englishwhiz.presentation.home.components.IconThemeSwitch
import com.javalon.englishwhiz.presentation.home.components.UtilButtons
import kotlinx.coroutines.InternalCoroutinesApi

var dictionaryStringBuilder = StringBuilder()

@ExperimentalMaterialApi
@InternalCoroutinesApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    scaffoldState: ScaffoldState,
    wordIndex: Int?,
    isBookmark: Boolean?,
    wordViewModel: WordModelViewModel,
    bookmarkViewModel: BookmarkViewModel,
    historyViewModel: HistoryViewModel,
    onToggleTheme: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val suggestions by wordViewModel.suggestions.collectAsState()
    val wordModelState by wordViewModel.wordState.collectAsState()
    var wordModel = wordModelState

    if (wordIndex != -1) {
        wordModel = if (isBookmark == true) {
            val bookmarkWordState by bookmarkViewModel.bookmarks.collectAsState()
            WordState(bookmarkWordState[wordIndex!!])
        } else {
            val historyWordState by historyViewModel.history.collectAsState()
            WordState(historyWordState[wordIndex!!])
        }
    }

        Column(modifier = Modifier.fillMaxSize()) {

            IconThemeSwitch(modifier = Modifier.align(Alignment.End)) {
                onToggleTheme()
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .height(64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    AutoCompleteTextField(
                        modifier = Modifier.fillMaxWidth(),
                        suggestions = suggestions,
                        onSearch = {
                            wordViewModel.prefixMatcher(it)
                            wordViewModel.searcher(it)
                        },
                        onClear = {
                            wordViewModel.clearSuggestions()
                        },
                        onDoneActionClick = {
                            keyboardController?.hide()
                        },
                        onItemClick = {
                            wordViewModel.searcher(it, true)
                            keyboardController?.hide()
                        },
                        itemContent = {
                            Text(
                                text = it,
                                color = MaterialTheme.colors.onSurface
                            )
                        }
                    )
                }
            }

            SearchComponent(wordModel, scaffoldState, wordViewModel)
        }
}


@ExperimentalMaterialApi
@InternalCoroutinesApi
@ExperimentalUnitApi
@Composable
fun SearchComponent(
    wordState: WordState,
    scaffoldState: ScaffoldState,
    wordViewModel: WordModelViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Search results",
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.surface
        ) {
            SearchResult(wordState, scaffoldState, wordViewModel)
        }
    }
}

@ExperimentalMaterialApi
@InternalCoroutinesApi
@ExperimentalUnitApi
@Composable
fun SearchResult(
    wordState: WordState,
    scaffoldState: ScaffoldState,
    wordViewModel: WordModelViewModel
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = wordState.wordModel?.word ?: "",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )

        SearchContent(wordState.wordModel, scaffoldState, wordViewModel)
    }
}

@ExperimentalMaterialApi
@InternalCoroutinesApi
@ExperimentalUnitApi
@Composable
fun SearchContent(
    wordModel: WordModel?,
    scaffoldState: ScaffoldState,
    wordViewModel: WordModelViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        UtilButtons(scaffoldState, wordViewModel)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        wordModel?.let {
            dictionaryStringBuilder = dictionaryStringBuilder.clear()
            dictionaryStringBuilder.append(it.word).append("\n")
            it.meanings?.forEachIndexed { index, meaning ->
                dictionaryStringBuilder.append(meaning.speechPart).append("\n")
                Text(
                    text = meaning.speechPart,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colors.onSurface
                )
                dictionaryStringBuilder.append("${index + 1}. ${meaning.def}").append("\n")
                Text(
                    text = "${index + 1}. ${meaning.def}",
                    style = MaterialTheme.typography.subtitle2,
                    lineHeight = TextUnit(18f, TextUnitType.Sp),
                    color = MaterialTheme.colors.onSurface
                )
                if (!meaning.labels.isNullOrEmpty()) {
                    val label = meaning.labels.map { label -> label.name }.toString()
                        .removePrefix("[")
                        .removeSuffix("]").replace(",", " •")
                    dictionaryStringBuilder.append(label).append("\n")
                    Text(
                        text = label,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Normal),
                        color = Color.Gray
                    )
                }
                if (!meaning.example.isNullOrEmpty()) {
                    val example = "Example: ${meaning.example}"
                    dictionaryStringBuilder.append(example).append("\n")
                    Text(
                        text = example,
                        fontStyle = FontStyle.Italic,
                        lineHeight = TextUnit(16f, TextUnitType.Sp),
                        style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Normal),
                        color = Color.Gray
                    )
                }
                if (!meaning.synonyms.isNullOrEmpty()) {
                    val synonym = "Synonym(s): ${
                        meaning.synonyms.toString()
                            .removePrefix("[")
                            .removeSuffix("]")
                    }"
                    dictionaryStringBuilder.append(synonym).append("\n")
                    Text(
                        text = synonym,
                        fontStyle = FontStyle.Italic,
                        lineHeight = TextUnit(16f, TextUnitType.Sp),
                        style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Normal),
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Divider(
                    thickness = 0.4.dp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}