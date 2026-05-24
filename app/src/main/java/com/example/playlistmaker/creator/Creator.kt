package com.example.playlistmaker.creator

import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.data.network.ITunesApiService
import com.example.playlistmaker.data.repository.TracksRepositoryImpl
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.domain.api.TracksRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Creator {
    private fun getNetworkClient(): NetworkClient {
        // Создаем Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ITunesApiService::class.java)
        return RetrofitNetworkClient(api)
    }

    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(getNetworkClient())
    }
}