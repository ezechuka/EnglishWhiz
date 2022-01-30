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
class HistoryViewModel @Inject constructor(val wordRepo: WordRepository): ViewModel() {
    private val _history = mutableStateOf(listOf<WordModel>())
    val history: State<List<WordModel>> = _history

    fun deleteWordModel(wordModel: WordModel) {
        viewModelScope.launch(Dispatchers.IO) {
            wordRepo.deleteBookmark(wordModel.toWordModelEntity())
            getAllHistory()
        }
    }

    fun getAllHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            wordRepo.getAllHistory().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d("VIEWMODEL-history", result.data.toString())
                        _history.value = result.data?.map { it.toWordModel() } ?: emptyList()
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