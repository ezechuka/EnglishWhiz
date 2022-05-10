package com.javalon.englishwhiz.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javalon.englishwhiz.domain.model.WordModel
import com.javalon.englishwhiz.domain.repository.DictionaryBaseRepository
import com.javalon.englishwhiz.domain.repository.WordBaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordModelViewModel @Inject constructor(
    private val wordRepo: WordBaseRepository,
    private val dictRepository: DictionaryBaseRepository
) : ViewModel() {
    var searchQuery = MutableStateFlow(String())
        private set

    var wordState = MutableStateFlow(WordState())
        private set

    var suggestions = MutableStateFlow(emptyList<String>())
        private set

    private var searchJob: Job? = null
    private var prefixMatchJob: Job? = null

    fun searcher(query: String, isWordClick: Boolean = false) {
        if (query.isBlank()) {
            clearSuggestions()
            return
        }
        searchJob?.cancel()

        searchJob = viewModelScope.launch(IO) {
            val result = dictRepository.search(query).firstOrNull()?.first()
            result?.let {
                wordState.value = WordState(it.toWordModel())
                if (isWordClick) insertHistory(it.toWordModel())
            }
        }
    }

    fun prefixMatcher(query: String) {
        clearSuggestions()

        prefixMatchJob?.cancel()
        prefixMatchJob = viewModelScope.launch(IO) {
            dictRepository.prefixMatch(query).collect { matches ->
                matches.let { match ->
                    suggestions.value = match.map { it.word }
                }
            }
        }
    }

    fun clearSuggestions() {
        suggestions.value = emptyList()
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