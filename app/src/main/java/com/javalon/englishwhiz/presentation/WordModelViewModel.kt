package com.javalon.englishwhiz.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javalon.englishwhiz.data.repository.WordRepository
import com.javalon.englishwhiz.domain.model.WordModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordModelViewModel @Inject constructor(private val wordRepo: WordRepository) : ViewModel() {
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf<WordModel?>(null)
    val state: State<WordModel?> = _state

    private val _matches = mutableStateOf(emptyList<String>())
    val matches: State<List<String>> = _matches

    private var searchJob: Job? = null

    fun searcher(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch(IO) {
            delay(500)
            val result = wordRepo.search(query).getOrNull(0)
            result?.let {
                _state.value = it.toWordModel()
            }
        }
    }

    fun prefixMatcher(query: String) {
        if (query.isEmpty()) {
            _matches.value = emptyList() // clear previous `matches` state
            return
        }

        viewModelScope.launch(IO) {
            wordRepo.prefixMatch(query).collectLatest { match ->
                Log.d("VIEWMODEL", match.map { it.word }.toString())
                _matches.value = match.map { it.word }
            }
        }
    }

    fun insertBookmark(wordModel: WordModel) {
        viewModelScope.launch(IO) {
            wordRepo.insertBookmark(wordModel.toBookmarkEntity())
        }
    }

    fun insertHistory(wordModel: WordModel) {
        viewModelScope.launch(IO) {
            wordRepo.insertHistory(wordModel.toHistoryEntity())
        }
    }
}