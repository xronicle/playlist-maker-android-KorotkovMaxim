package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.DatabaseMock
import com.example.playlistmaker.domain.api.SearchHistoryRepository

class SearchHistoryRepositoryImpl : SearchHistoryRepository {
    private val database = DatabaseMock

    override fun getHistoryRequests(): List<String> = database.getHistory()
    override fun addToHistory(word: String) = database.addToHistory(word)
}