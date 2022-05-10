package com.javalon.englishwhiz.presentation.home.components

import android.content.Intent
import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.buildAnnotatedString
import androidx.hilt.navigation.compose.hiltViewModel
import com.javalon.englishwhiz.presentation.home.TTSListener
import com.javalon.englishwhiz.presentation.home.WordModelViewModel
import com.javalon.englishwhiz.presentation.home.dictionaryStringBuilder
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun UtilButtons(scaffold: ScaffoldState, viewModel: WordModelViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    CopyButton {
        if (dictionaryStringBuilder.toString().isNotEmpty()) {
            clipboardManager.setText(buildAnnotatedString {
                append(dictionaryStringBuilder.toString())
            })

            coroutineScope.launch {
                scaffold.snackbarHostState.showSnackbar(message = "Copied")
            }
        }
    }

    var clickedState by remember { mutableStateOf(false) }
    val ttsListener by remember {
        mutableStateOf(TTSListener(context) {
            clickedState = false
        })
    }
    LocalLifecycleOwner.current.lifecycle.addObserver(ttsListener)
    SpeakButton(clicked = clickedState) {
        clickedState = !clickedState

        if (clickedState) {
            ttsListener.speak(dictionaryStringBuilder.toString())
            coroutineScope.launch {
                scaffold.snackbarHostState.showSnackbar(message = "Speaking")
            }
        } else {
            ttsListener.stop()
        }
    }

    SaveButton {
        val wordModel = viewModel.wordState.value.wordModel
        if (wordModel != null) {
            viewModel.insertBookmark(wordModel)
            coroutineScope.launch {
                scaffold.snackbarHostState.showSnackbar(message = "Saved")
            }
        }
    }

    ShareButton {
        val intentFileChooser = Intent(Intent.ACTION_SEND)
        intentFileChooser.putExtra(
            Intent.EXTRA_TEXT,
            "Please download and rate us now: www.google.com"
        )
        intentFileChooser.type = "text/plain"
        val intent = Intent.createChooser(intentFileChooser, "Share EnglishWhiz")
        context.startActivity(intent)
    }
}