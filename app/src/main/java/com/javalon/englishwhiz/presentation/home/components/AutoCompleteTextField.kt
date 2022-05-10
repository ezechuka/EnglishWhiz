package com.javalon.englishwhiz.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.javalon.englishwhiz.R

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun <T> AutoCompleteTextField(
    modifier: Modifier = Modifier,
    suggestions: List<T>,
    onSearch: (String) -> Unit,
    onClear: () -> Unit,
    onDoneActionClick: () -> Unit = {},
    onItemClick: (T) -> Unit = {},
    itemContent: @Composable (T) -> Unit = {}
) {
    var searchQuery by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var expandedState by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandedState,
        onExpandedChange = { expandedState = !expandedState },
        modifier = modifier.fillMaxWidth()
    ) {
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                if (it.text.isNotBlank())
                    onSearch(it.text)
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
                Icon(
                    painter = painterResource(R.drawable.search), contentDescription = null
                )
            },
            trailingIcon = {
                if (searchQuery.text.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = TextFieldValue()
                        onClear()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.clear),
                            contentDescription = null
                        )
                    }
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                backgroundColor = MaterialTheme.colors.surface,
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
            modifier = Modifier.fillMaxWidth()
        )

        if (suggestions.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expandedState,
                onDismissRequest = { expandedState = false },
                modifier = Modifier.background(
                    MaterialTheme.colors.surface
                )
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(onClick = {
                        searchQuery = TextFieldValue(
                            label.toString(),
                            selection = TextRange(label.toString().length)
                        )
                        onItemClick(label)
                        expandedState = false
                    }) {
                        itemContent(label)
                    }
                }
            }
        }
    }
}