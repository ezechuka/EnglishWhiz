package com.javalon.englishwhiz.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javalon.englishwhiz.data.repository.WordRepository
import com.javalon.englishwhiz.domain.model.WordModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val wordRepo: WordRepository): ViewModel() {
    var history = MutableStateFlow(listOf<WordModel>())
        private set

    fun deleteHistory(wordModel: WordModel) {
        viewModelScope.launch(Dispatchers.IO) {
            wordRepo.deleteHistory(wordModel.toHistoryEntity())
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            wordRepo.getAllHistory().collect { result ->
                history.value = result.map { it.toWordModel() }.reversed()
            }

        }
    }
}