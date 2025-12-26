package com.example.playlistmaker.ui.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.components.TabTopBar

@Composable
fun FavoritesScreen(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            topBar = { TabTopBar(stringResource(R.string.favorites), onBack) }
        ) { paddingValues ->
            paddingValues // Заглушка
        }
    }
}