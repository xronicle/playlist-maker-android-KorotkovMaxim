package com.example.playlistmaker.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.playlistmaker.data.network.ITunesApiService
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.data.database.AppDatabase
import com.example.playlistmaker.data.preferences.SearchHistoryPreferences
import com.example.playlistmaker.data.repository.PlaylistsRepositoryImpl
import com.example.playlistmaker.data.repository.SearchHistoryRepositoryImpl
import com.example.playlistmaker.data.repository.TracksRepositoryImpl
import com.example.playlistmaker.domain.api.PlaylistsRepository
import com.example.playlistmaker.domain.api.SearchHistoryRepository
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.ui.screens.playlist.PlaylistViewModel
import com.example.playlistmaker.ui.screens.playlists.PlaylistsViewModel
import com.example.playlistmaker.ui.screens.search.SearchViewModel
import com.example.playlistmaker.ui.screens.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val Context.dataStore by preferencesDataStore(name = "playlist_maker_preferences")

val dataModule = module {

    single<ITunesApiService> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(api = get())
    }

    single {
        Room.databaseBuilder(
            get<Context>(),
            AppDatabase::class.java,
            "playlists_maker_database.db"
        ).build()
    }

    single { get<Context>().dataStore }

    single { SearchHistoryPreferences(dataStore = get()) }
}

val repositoryModule = module {

    single<TracksRepository> {
        TracksRepositoryImpl(networkClient = get(), database = get())
    }

    single<PlaylistsRepository> {
        PlaylistsRepositoryImpl(database = get())
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(preferences = get())
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

    viewModel { (id: Long) ->
        PlaylistViewModel(
            playlistsRepository = get(),
            playlistId = id,
            tracksRepository = get()
        )
    }
}