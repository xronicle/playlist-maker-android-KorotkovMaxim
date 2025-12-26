package com.example.playlistmaker.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.api.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(
    private val tracksRepository : TracksRepository
) : ViewModel() {
    private val _allTracksScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val allTracksScreenState = _allTracksScreenState.asStateFlow()

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _allTracksScreenState.update { SearchState.Loading }
                val list = tracksRepository.searchTracks("")
                _allTracksScreenState.update { SearchState.Success(list) }
            } catch (e: IOException) {
                _allTracksScreenState.update { SearchState.Error(e.message.toString()) }
            }
        }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(Creator.getRepository()) as T
                }
            }
    }
}