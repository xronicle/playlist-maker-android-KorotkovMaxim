package com.example.playlistmaker.creator

import com.example.playlistmaker.data.TrackRepositoryImpl
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.domain.api.TracksRepository

object Creator {
    fun getRepository(): TracksRepository {
        return TrackRepositoryImpl(RetrofitNetworkClient(Storage()))
    }
}