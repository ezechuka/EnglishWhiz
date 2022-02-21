package com.javalon.englishwhiz.ui

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import com.javalon.englishwhiz.R
import com.javalon.englishwhiz.domain.model.UtilItem
import com.javalon.englishwhiz.domain.model.WordModel
import com.javalon.englishwhiz.presentation.BookmarkViewModel
import com.javalon.englishwhiz.presentation.HistoryViewModel
import com.javalon.englishwhiz.presentation.WordModelViewModel
import com.javalon.englishwhiz.ui.theme.blueBGDay
import com.javalon.englishwhiz.ui.theme.blueText
import com.javalon.englishwhiz.ui.theme.cardBGDay
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.*

var dictionaryStringBuilder = StringBuilder()
var dropDownOptions = mutableStateOf(listOf<String>())

@InternalCoroutinesApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    wordViewModel: WordModelViewModel,
    bookmarkViewModel: BookmarkViewModel,
    historyViewModel: HistoryViewModel,
    scaffoldState: ScaffoldState,
    textToSpeechEngine: TextToSpeech,
    onInit: Boolean,
    wordIndex: Int?,
    isBookmark: Boolean?
) {
    val wordModelState = remember { mutableStateOf(wordViewModel.state) }
    if (wordIndex != -1) {
        if (isBookmark == true) {
            val wordModel = bookmarkViewModel.bookmarks.value[wordIndex!!]
            wordModelState.value = remember { mutableStateOf(wordModel) }
        } else {
            val wordModel = historyViewModel.history.value[wordIndex!!]
            wordModelState.value = remember { mutableStateOf(wordModel) }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            dropDownOptions.value = wordViewModel.matches.value
            val keyboardController = LocalSoftwareKeyboardController.current
            AutoCompleteTextField(
                modifier = Modifier.fillMaxWidth(),
                suggestions = dropDownOptions.value,
                viewModel = wordViewModel,
                onDoneActionClick = {
                    keyboardController?.hide()
                },
                onItemClick = {
                    wordViewModel.searcher(it, true)
//                    wordViewModel.state.value?.let { searchHistory -> wordViewModel.insertHistory(searchHistory) }
                },
                itemContent = {
                    Text(
                        text = it,
                        color = blueText,
                    )
                }
            )
        }

        SearchComponent(
            wordModelState.value.value,
            scaffoldState, textToSpeechEngine, onInit, wordViewModel
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun <T> AutoCompleteTextField(
    modifier: Modifier = Modifier,
    suggestions: List<T>,
    onDoneActionClick: () -> Unit = {},
    viewModel: WordModelViewModel,
    onItemClick: (T) -> Unit = {},
    itemContent: @Composable (T) -> Unit = {}
) {
    Column {
        var rowSize by remember { mutableStateOf(Size.Zero) }
        val expandedState = remember { mutableStateOf(false) }
        TextField(
            value = viewModel.searchQuery.value,
            onValueChange = {
                viewModel.prefixMatcher(it)
                viewModel.searcher(it)
                if (it.isNotEmpty()) expandedState.value = true
            },
            singleLine = true,
            placeholder = {
                Text(
                    text = "Search by word...",
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray
                )
            },
            leadingIcon = {
                Icon(painter = painterResource(R.drawable.search), contentDescription = null)
            },
            trailingIcon = {
                if (viewModel.searchQuery.value.isNotEmpty()) {
                    IconButton(onClick = { viewModel.searcher("") }) {
                        Icon(
                            painter = painterResource(R.drawable.clear),
                            contentDescription = null
                        )
                    }
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                backgroundColor = cardBGDay,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardActions = KeyboardActions(onDone = {
                onDoneActionClick()
            }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = modifier
                .onGloballyPositioned { layoutCoordinates ->
                    rowSize = layoutCoordinates.size.toSize()
                },
        )

        DropdownMenu(
            expanded = expandedState.value,
            onDismissRequest = { expandedState.value = false },
            modifier = Modifier
                .width(with(LocalDensity.current) {
                    rowSize.width.toDp()
                })
                .background(blueBGDay),
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    onItemClick(label)
                    expandedState.value = false
                }) {
                    itemContent(label)
                }
            }
        }
    }
}

@InternalCoroutinesApi
@ExperimentalUnitApi
@Composable
fun SearchComponent(
    wordModel: WordModel?,
    scaffoldState: ScaffoldState,
    textToSpeechEngine: TextToSpeech,
    onInit: Boolean,
    viewModel: WordModelViewModel
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
            backgroundColor = cardBGDay
        ) {
            SearchResult(wordModel, scaffoldState, textToSpeechEngine, onInit, viewModel)
        }
    }
}

@InternalCoroutinesApi
@ExperimentalUnitApi
@Composable
fun SearchResult(
    wordModel: WordModel?,
    scaffold: ScaffoldState,
    textToSpeechEngine: TextToSpeech,
    onInit: Boolean,
    viewModel: WordModelViewModel
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = wordModel?.word ?: "",
            style = MaterialTheme.typography.h6,
            color = blueText,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )

        SearchContent(wordModel, scaffold, textToSpeechEngine, onInit, viewModel)
    }
}

@InternalCoroutinesApi
@ExperimentalUnitApi
@Composable
fun SearchContent(
    wordModel: WordModel?,
    scaffold: ScaffoldState,
    textToSpeechEngine: TextToSpeech,
    onInit: Boolean,
    viewModel: WordModelViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val clipboardManager = LocalClipboardManager.current
        val context = LocalContext.current
        val utilItemList = provideUtilItemList(clipboardManager, context, viewModel)
        UtilItemComponent(utilItemList, scaffold, textToSpeechEngine, onInit)
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
                    color = Color.Black
                )
                dictionaryStringBuilder.append("${index + 1}. ${meaning.def}").append("\n")
                Text(
                    text = "${index + 1}. ${meaning.def}",
                    style = MaterialTheme.typography.subtitle2,
                    lineHeight = TextUnit(18f, TextUnitType.Sp),
                    color = Color.Black
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

@Composable
fun UtilItemComponent(
    item: List<UtilItem>,
    scaffold: ScaffoldState,
    textToSpeechEngine: TextToSpeech,
    onInit: Boolean
) {
    val coroutineScope = rememberCoroutineScope()
    Card(
        elevation = 0.dp,
        modifier = Modifier.clickable {
            item[0].utilItemClick()
            coroutineScope.launch {
                item[0].message?.let { scaffold.snackbarHostState.showSnackbar(message = it) }
            }
        },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(8.dp)
                .size(48.dp)
        ) {
            Icon(painterResource(id = item[0].itemIcon), contentDescription = item[0].itemText)
            Text(
                text = item[0].itemText,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }

    val clickedState = remember { mutableStateOf(false) }
    Card(
        elevation = 0.dp,
        modifier = Modifier.clickable {
            if (onInit) {
                textToSpeechEngine.language = Locale.UK
            }

            val textToSpeak = dictionaryStringBuilder.toString()
            if (textToSpeak.isNotBlank()) {
                textToSpeechEngine.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "tts1")
            }

            clickedState.value = !clickedState.value
        },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(8.dp)
                .size(48.dp)
        ) {
            if (clickedState.value) {
                Icon(
                    painterResource(id = R.drawable.speak_off),
                    contentDescription = item[1].itemText
                )
                coroutineScope.launch {
                    item[1].message?.let { scaffold.snackbarHostState.showSnackbar(message = it) }
                }
            } else {
                Icon(
                    painterResource(id = item[1].itemIcon),
                    contentDescription = item[1].itemText
                )
                textToSpeechEngine.stop()
            }
            Text(
                text = if (clickedState.value) "Stop" else item[1].itemText,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle2
            )
            textToSpeechEngine.setOnUtteranceProgressListener(object :
                UtteranceProgressListener() {

                override fun onError(p0: String?) {
                }

                override fun onStart(p0: String?) {

                }

                override fun onDone(p0: String?) {
                    clickedState.value = false
                }
            })
        }
    }

    Card(
        elevation = 0.dp,
        modifier = Modifier.clickable {
            item[2].utilItemClick()
            coroutineScope.launch {
                item[2].message?.let { scaffold.snackbarHostState.showSnackbar(message = it) }
            }
        },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(8.dp)
                .size(48.dp)
        ) {
            Icon(painterResource(id = item[2].itemIcon), contentDescription = item[2].itemText)
            Text(
                text = item[2].itemText,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }

    Card(
        elevation = 0.dp,
        modifier = Modifier.clickable {
            item[3].utilItemClick()
            coroutineScope.launch {
                item[3].message?.let { scaffold.snackbarHostState.showSnackbar(message = it) }
            }
        },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(8.dp)
                .size(48.dp)
        ) {
            Icon(painterResource(id = item[3].itemIcon), contentDescription = item[3].itemText)
            Text(
                text = item[3].itemText,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

@InternalCoroutinesApi
fun provideUtilItemList(
    clipboardManager: ClipboardManager,
    context: Context,
    viewModel: WordModelViewModel
): List<UtilItem> {

    val copy = {
        clipboardManager.setText(buildAnnotatedString {
            append(dictionaryStringBuilder.toString())
        })
    }

    val save = {
        val wordModel = viewModel.state.value
        if (wordModel != null) {
            viewModel.insertBookmark(wordModel)
        }
    }

    val share = {
        val intentFileChooser = Intent(Intent.ACTION_SEND)
        intentFileChooser.putExtra(
            Intent.EXTRA_TEXT,
            "Please download and rate us now: www.google.com"
        )
        intentFileChooser.type = "text/plain"
        val intent = Intent.createChooser(intentFileChooser, "Share EnglishWhiz")
        context.startActivity(intent)
    }

    return listOf(
        UtilItem("copy", "Copied", R.drawable.copy, copy),
        UtilItem("speak", "Now reading", itemIcon = R.drawable.speak),
        UtilItem("save", "Saved", itemIcon = R.drawable.save, save),
        UtilItem("share", itemIcon = R.drawable.share, utilItemClick = share)
    )
}