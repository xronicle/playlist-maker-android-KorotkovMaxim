package com.example.playlistmaker.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.playlistmaker.R
import com.example.playlistmaker.SearchActivity
import com.example.playlistmaker.utils.startActivity
import com.example.playlistmaker.SettingsActivity
import com.example.playlistmaker.ui.components.List
import com.example.playlistmaker.ui.components.ListItem

@Preview
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val playlistsText = stringResource(R.string.playlists)
    val favoritesText = stringResource(R.string.favorites)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        List(
            ListItem(
                painterResource(R.drawable.loupe),
                "search",
                stringResource(R.string.search)
            ) { context.startActivity(SearchActivity::class.java) },
            ListItem(
                painterResource(R.drawable.playlist),
                "playlists",
                stringResource(R.string.playlists)
            ) {
                Toast.makeText(
                    context,
                    "Нажата кнопка \"$playlistsText\"",
                    Toast.LENGTH_SHORT
                ).show()
            },
            ListItem(
                painterResource(R.drawable.like),
                "like",
                stringResource(R.string.favorites)
            ) {
                Toast.makeText(
                    context,
                    "Нажата кнопка \"$favoritesText\"",
                    Toast.LENGTH_SHORT
                ).show()
            },
            ListItem(
                painterResource(R.drawable.settings),
                "settings",
                stringResource(R.string.settings)
            ) { context.startActivity(SettingsActivity::class.java) }
        )
    }
}