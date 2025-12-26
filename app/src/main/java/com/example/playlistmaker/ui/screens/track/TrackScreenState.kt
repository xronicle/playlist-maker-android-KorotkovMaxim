package com.example.playlistmaker.ui.screens.track

import com.example.playlistmaker.domain.models.Track

sealed class TrackScreenState {
    object Loading : TrackScreenState()
    data class Content(
        val trackModel: Track
    ) : TrackScreenState()
}