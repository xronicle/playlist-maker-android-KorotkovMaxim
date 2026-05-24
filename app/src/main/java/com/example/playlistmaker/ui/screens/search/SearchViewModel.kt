package com.example.playlistmaker.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.data.repository.SearchHistoryRepositoryImpl
import com.example.playlistmaker.domain.api.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val tracksRepository: TracksRepository
) : ViewModel() {

    private val searchHistoryRepository = SearchHistoryRepositoryImpl()

    private val _searchQuery = MutableStateFlow("")

    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(1000L)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) {
                        performSearch(query)
                    } else {
                        _searchScreenState.update { SearchState.Initial }
                    }
                }
        }
    }

    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    private fun performSearch(request: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }
                searchHistoryRepository.addToHistory(request)

                val list = tracksRepository.searchTracks(expression = request)
                _searchScreenState.update { SearchState.Success(foundList = list) }
            } catch (e: IOException) {
                _searchScreenState.update { SearchState.Fail(e.message ?: "Unknown error") }
            }
        }
    }

    fun clearSearch() {
        updateQuery("")
        _searchScreenState.update { SearchState.Initial }
    }

    fun getHistoryList(): List<String> = searchHistoryRepository.getHistoryRequests()
}