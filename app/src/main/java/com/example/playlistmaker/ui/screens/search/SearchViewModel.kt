package com.example.playlistmaker.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.api.SearchHistoryRepository
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.domain.models.Resource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val tracksRepository: TracksRepository,
    private val searchHistoryRepository: SearchHistoryRepository
) : ViewModel() {

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
        _searchScreenState.update { SearchState.Searching }

        viewModelScope.launch {
            tracksRepository.searchTracks(request).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        searchHistoryRepository.addToHistory(request)
                        _searchScreenState.update {
                            SearchState.Success(foundList = resource.data ?: emptyList())
                        }
                    }

                    is Resource.Error -> {
                        _searchScreenState.update {
                            SearchState.Fail(error = resource.message ?: "Unknown error")
                        }
                    }
                }
            }
        }
    }

    fun clearSearch() {
        updateQuery("")
        _searchScreenState.update { SearchState.Initial }
    }

    fun getHistoryList(): List<String> = searchHistoryRepository.getHistoryRequests()
}