package com.example.playlistmaker.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.components.List
import com.example.playlistmaker.ui.components.ListItem

@Composable
fun MainScreen(navigateToSearch: () -> Unit, navigateToSettings: () -> Unit) {
    val context = LocalContext.current
    val playlistsText = stringResource(R.string.playlists)
    val favoritesText = stringResource(R.string.favorites)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(
                    vertical = 14.dp,
                    horizontal = 16.dp
                )
            )
            List(
                textStyle = MaterialTheme.typography.titleMedium,
                paddings = PaddingValues(16.dp, 8.dp),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                items = listOf(
                    ListItem(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.loupe),
                                contentDescription =stringResource(R.string.search),
                                modifier = Modifier
                                    .size(24.dp)
                                    .aspectRatio(1f),
                                tint = MaterialTheme.colorScheme.onBackground,
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.chevron_right),
                                contentDescription =stringResource(R.string.chevron_right),
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        text = stringResource(R.string.search)
                    ) { navigateToSearch() },
                    ListItem(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.playlist),
                                contentDescription =stringResource(R.string.playlists),
                                modifier = Modifier
                                    .size(24.dp)
                                    .aspectRatio(1f),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.chevron_right),
                                contentDescription =stringResource(R.string.chevron_right),
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        text = stringResource(R.string.playlists)
                    ) {
                        Toast.makeText(
                            context,
                            "Нажата кнопка \"$playlistsText\"",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    ListItem(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.like),
                                contentDescription =stringResource(R.string.favorites),
                                modifier = Modifier
                                    .size(24.dp)
                                    .aspectRatio(1f),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.chevron_right),
                                contentDescription =stringResource(R.string.chevron_right),
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        text = stringResource(R.string.favorites)
                    ) {
                        Toast.makeText(
                            context,
                            "Нажата кнопка \"$favoritesText\"",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    ListItem(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.settings),
                                contentDescription =stringResource(R.string.settings),
                                modifier = Modifier
                                    .size(24.dp)
                                    .aspectRatio(1f),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.chevron_right),
                                contentDescription = stringResource(R.string.chevron_right),
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        text = stringResource(R.string.settings)
                    ) { navigateToSettings() }
                )
            )
        }
    }
}