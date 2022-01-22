package com.javalon.englishwhiz.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javalon.englishwhiz.data.local.entity.WordModelEntity
import com.javalon.englishwhiz.data.repository.WordRepository
import com.javalon.englishwhiz.domain.model.WordModel
import com.javalon.englishwhiz.util.Resource
import com.javalon.englishwhiz.util.Trie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordModelViewModel @Inject constructor(private val wordRepo: WordRepository) : ViewModel() {
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(WordState())
    val state: State<WordState> = _state

    private val _matches = mutableStateOf(emptyList<String>())
    val matches: State<List<String>> = _matches

    private val _isRetrieved = MutableSharedFlow<Boolean>()
    val isRetrieved: SharedFlow<Boolean> = _isRetrieved.asSharedFlow()

    private val _searchEvent = MutableSharedFlow<Boolean>()
    val searchEvent: SharedFlow<Boolean> = _searchEvent.asSharedFlow()

    val trieDictionary = Trie<Char>()

    var searchJob: Job? = null

    fun search(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Default) {
            val wordState = trieDictionary.contains(query.toList())
            if (wordState.isContained) {
                Log.d("VIEWMODEL", wordState.wordModel?.meanings.toString())
                _state.value = wordState
                _searchEvent.emit(true)
            }
        }
    }

    fun prefixMatch(query: String) {
        if (query.isEmpty()) {
            _matches.value = emptyList() // clear previous `matches` state
            return
        }
        val matches = trieDictionary.prefixMatch(query.toList())
        val results = mutableListOf<String>()
        for (i in matches.indices) {
            if (i == 5)
                break
            else {
                results.add(matches[i].joinToString(""))
            }
        }
        Log.d("VIEWMODEL", results.toString())
        _matches.value = results
    }

    fun insertWordModel(wordModel: WordModel) {
        viewModelScope.launch(IO) {
            wordRepo.insertWordModel(wordModel.toWordModelEntity())
        }
    }

    fun getAllBookmark() {
        viewModelScope.launch(IO) {
            val result = wordRepo.getAllBookmark()
        }
    }

    init {
        viewModelScope.launch(Default) {
            wordRepo.readFromJsonStream().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.forEach { (key, value) ->
                            value.forEach { wordModel ->
                                wordModel.word?.toList()?.let { trieDictionary.insert(it, wordModel) }
                            }
                        }
                        Log.d("VIEWMODEL", result.message ?: "SUCCESS")
                        _isRetrieved.emit(true)
                    }

                    is Resource.Loading -> {
                        Log.d("VIEWMODEL", result.message ?: "Loading")
                        _isRetrieved.emit(false)
                    }

                    is Resource.Error -> {
                        Log.d("VIEWMODEL", result.message ?: "")
                    }
                }
            }
        }
    }
}