package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.preferences.SearchHistoryPreferences
import com.example.playlistmaker.domain.api.SearchHistoryRepository
import kotlinx.coroutines.runBlocking

class SearchHistoryRepositoryImpl(
    private val preferences: SearchHistoryPreferences
) : SearchHistoryRepository {

    override fun getHistoryRequests(): List<String> {
        return runBlocking { preferences.getEntries() }
    }

    override fun addToHistory(word: String) {
        preferences.addEntry(word)
    }
}