package com.javalon.englishwhiz.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javalon.englishwhiz.data.repository.DictionaryRepository
import com.javalon.englishwhiz.data.repository.WordRepository
import com.javalon.englishwhiz.domain.model.WordModel
import com.javalon.englishwhiz.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordModelViewModel @Inject constructor(private val wordRepo: WordRepository, private val dictRepository: DictionaryRepository) : ViewModel() {
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf<WordModel?>(null)
    val state: State<WordModel?> = _state

    private val _matches = mutableStateOf(emptyList<String>())
    val matches: State<List<String>> = _matches

    private var searchJob: Job? = null
    private var prefixMatchJob: Job? = null

    fun searcher(query: String, isWordClick: Boolean = false) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch(IO) {
            val result = dictRepository.search(query).getOrNull(0)
            result?.let {
                _state.value = it.toWordModel()
                if (isWordClick) insertHistory(it.toWordModel())
            }
        }
    }

    fun prefixMatcher(query: String) {
        if (query.isEmpty()) {
            _matches.value = emptyList() // clear previous `matches` state
            return
        }

        prefixMatchJob?.cancel()
        prefixMatchJob = viewModelScope.launch(IO) {
            dictRepository.prefixMatch(query).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        val matches = result.data
                        matches?.let { match ->
                            _matches.value = match.map { it.word }
                        }
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {}
                }
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