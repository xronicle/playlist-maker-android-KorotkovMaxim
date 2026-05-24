package com.example.playlistmaker.di

import com.example.playlistmaker.data.network.ITunesApiService
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.data.repository.PlaylistsRepositoryImpl
import com.example.playlistmaker.data.repository.SearchHistoryRepositoryImpl
import com.example.playlistmaker.data.repository.TracksRepositoryImpl
import com.example.playlistmaker.domain.api.PlaylistsRepository
import com.example.playlistmaker.domain.api.SearchHistoryRepository
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.ui.screens.playlists.PlaylistsViewModel
import com.example.playlistmaker.ui.screens.search.SearchViewModel
import com.example.playlistmaker.ui.screens.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Модуль для работы с данными (Сеть)
val dataModule = module {
    single<ITunesApiService> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            // Убедись, что у тебя подключена библиотека Gson (com.squareup.retrofit2:converter-gson)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(api = get()) // get() сам найдет ITunesApiService выше
    }
}

// Модуль для репозиториев
val repositoryModule = module {
    single<TracksRepository> {
        TracksRepositoryImpl(networkClient = get())
    }

    single<PlaylistsRepository> {
        PlaylistsRepositoryImpl()
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl()
    }
}

val viewModelModule = module {
    viewModel {
        SearchViewModel(
            tracksRepository = get(),
            searchHistoryRepository = get()
        )
    }

    viewModel {
        PlaylistsViewModel(playlistsRepository = get(), tracksRepository = get())
    }

    viewModel {
        SettingsViewModel(application = get())
    }
}