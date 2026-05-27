package com.example.playlistmaker.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>,
    private val coroutineScope: CoroutineScope = CoroutineScope(CoroutineName("search-history-preferences") + SupervisorJob())
) {
    companion object {
        val SEARCH_HISTORY_KEY = stringPreferencesKey("search_history")
        private const val MAX_ENTRIES = 10
        private const val SEPARATOR = ","
    }

    fun addEntry(word: String) {
        if (word.isEmpty()) return

        coroutineScope.launch {
            dataStore.edit { preferences ->
                val historyString = preferences[SEARCH_HISTORY_KEY].orEmpty()
                val history = if (historyString.isNotEmpty()) {
                    historyString.split(SEPARATOR).toMutableList()
                } else {
                    mutableListOf()
                }

                history.remove(word)
                history.add(0, word)

                val subList = history.take(MAX_ENTRIES)
                val updatedString = subList.joinToString(SEPARATOR)

                preferences[SEARCH_HISTORY_KEY] = updatedString
            }
        }
    }

    suspend fun getEntries(): List<String> {
        val preferences = dataStore.data.first()
        val historyString = preferences[SEARCH_HISTORY_KEY].orEmpty()
        return if (historyString.isNotEmpty()) {
            historyString.split(SEPARATOR)
        } else {
            emptyList()
        }
    }
}