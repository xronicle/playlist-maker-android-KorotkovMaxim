package com.example.playlistmaker.ui.screens.track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.api.TracksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrackViewModel(
    private val trackId: String,
    private val repository: TracksRepository
) : ViewModel() {
    private val _loadingStateFlow = MutableStateFlow(TrackScreenState.Loading)
    val loadingStateFlow = _loadingStateFlow.asStateFlow()

    companion object {
        fun getViewModelFactory(trackId: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository = Creator.getRepository()
                TrackViewModel(trackId, repository)
            }
        }
    }
}