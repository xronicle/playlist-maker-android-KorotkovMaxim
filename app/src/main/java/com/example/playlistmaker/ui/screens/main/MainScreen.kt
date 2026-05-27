package com.example.playlistmaker.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.components.ListItem
import com.example.playlistmaker.ui.components.OptionsList

@Composable
fun MainScreen(
    navigateToSearch: () -> Unit, navigateToSettings: () -> Unit,
    navigateToPlaylists: () -> Unit, navigateToFavorites: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
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
        OptionsList(
            paddings = PaddingValues(16.dp, 8.dp),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            itemsPaddings = PaddingValues(vertical = 20.dp, horizontal = 12.dp),
            items = listOf(
                ListItem(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.loupe),
                            contentDescription = stringResource(R.string.search),
                            modifier = Modifier
                                .size(24.dp)
                                .aspectRatio(1f),
                            tint = MaterialTheme.colorScheme.onBackground,
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
                    content = {
                        Text(
                            text = stringResource(R.string.search),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                ) { navigateToSearch() },
                ListItem(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.playlist),
                            contentDescription = stringResource(R.string.playlists),
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
                    content = {
                        Text(
                            text = stringResource(R.string.playlists),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                ) { navigateToPlaylists() },
                ListItem(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.like),
                            contentDescription = stringResource(R.string.favorites),
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
                    content = {
                        Text(
                            text = stringResource(R.string.favorites),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                ) { navigateToFavorites() },
                ListItem(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.settings),
                            contentDescription = stringResource(R.string.settings),
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
                    content = {
                        Text(
                            text = stringResource(R.string.settings),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                ) { navigateToSettings() }
            )
        )
    }
}