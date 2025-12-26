package com.example.playlistmaker.creator

import com.example.playlistmaker.data.TracksRepositoryImpl
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.domain.api.TracksRepository

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(Storage()))
    }
}