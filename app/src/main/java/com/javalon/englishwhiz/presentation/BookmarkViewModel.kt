package com.javalon.englishwhiz.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javalon.englishwhiz.data.repository.WordRepository
import com.javalon.englishwhiz.domain.model.WordModel
import com.javalon.englishwhiz.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(val wordRepo: WordRepository): ViewModel() {
    private val _bookmarks = mutableStateOf(listOf<WordModel>())
    val bookmarks: State<List<WordModel>> = _bookmarks

    fun deleteBookmark(wordModel: WordModel) {
        viewModelScope.launch(Dispatchers.IO) {
            wordRepo.deleteBookmark(wordModel.toBookmarkEntity())
            getAllBookmark()
        }
    }

    fun getAllBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            wordRepo.getAllBookmark().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d("VIEWMODEL-bookmark", result.data.toString())
                        _bookmarks.value = result.data?.map { it.toWordModel() } ?: emptyList()
                    }

                    is Resource.Loading -> {
                    }

                    is Resource.Error -> {
                    }
                }
            }

        }
    }
}