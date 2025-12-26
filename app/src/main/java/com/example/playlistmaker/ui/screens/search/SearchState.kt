package com.example.playlistmaker.ui.screens.search

import com.example.playlistmaker.domain.models.Track

sealed class SearchState {
    object Initial : SearchState()
    object Loading : SearchState()
    data class Success(val foundList: List<Track>) : SearchState()
    data class Error(val error: String) : SearchState()
}