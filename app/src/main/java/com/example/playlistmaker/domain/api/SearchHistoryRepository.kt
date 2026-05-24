package com.example.playlistmaker.domain.api

interface SearchHistoryRepository {
    fun getHistoryRequests(): List<String>
    fun addToHistory(word: String)
}