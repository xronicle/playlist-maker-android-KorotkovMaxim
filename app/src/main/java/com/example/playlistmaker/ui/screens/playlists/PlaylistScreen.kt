package com.example.playlistmaker.ui.screens.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.ui.components.TrackListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaylistViewModel,
    navigateBack: () -> Unit,
    navigateToTrack: (Long) -> Unit
) {
    val playlist by viewModel.playlist.collectAsState(initial = null)

    var showBottomSheet by remember { mutableStateOf(false) }
    var showDeletePlaylistDialog by remember { mutableStateOf(false) }
    var showDeleteTrackDialog by remember { mutableStateOf<Track?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navigateBack() },
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        if (playlist != null) {
            val currentPlaylist = playlist!!
            val tracks = currentPlaylist.tracks

            val totalMinutes = tracks.sumOf { track ->
                val parts = track.trackTime.split(":")
                if (parts.size == 2) {
                    val minutes = parts[0].toIntOrNull() ?: 0
                    val seconds = parts[1].toIntOrNull() ?: 0
                    minutes * 60 + seconds
                } else {
                    0
                }
            } / 60

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF333333)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = currentPlaylist.imageUri,
                    contentDescription = stringResource(R.string.label_playlist),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.add_photo),
                    error = painterResource(id = R.drawable.add_photo)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Text(
                    text = currentPlaylist.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (currentPlaylist.description.isNotEmpty()) {
                    Text(
                        text = currentPlaylist.description,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(
                    text = stringResource(R.string.playlist_stats, totalMinutes, tracks.size),
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(R.string.menu),
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { showBottomSheet = true }
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = tracks, key = { it.id }) { track ->
                    TrackListItem(
                        track = track,
                        onClick = { navigateToTrack(track.id) },
                        onLongClick = { showDeleteTrackDialog = track }
                    )
                }
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState,
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.share),
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {}
                                .padding(16.dp),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = stringResource(R.string.edit_info),
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { }
                                .padding(16.dp),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = stringResource(R.string.remove_playlist),
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showBottomSheet = false
                                    showDeletePlaylistDialog = true
                                }
                                .padding(16.dp),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            if (showDeletePlaylistDialog) {
                AlertDialog(
                    onDismissRequest = { showDeletePlaylistDialog = false },
                    title = {
                        Text(
                            text = stringResource(
                                R.string.delete_playlist_dialog_title,
                                currentPlaylist.name
                            ),
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.deletePlaylist(currentPlaylist.id)
                            showDeletePlaylistDialog = false
                            navigateBack()
                        }) {
                            Text(stringResource(R.string.yes), fontSize = 16.sp)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeletePlaylistDialog = false }) {
                            Text(stringResource(R.string.no), fontSize = 16.sp)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.surface
                )
            }

            if (showDeleteTrackDialog != null) {
                AlertDialog(
                    onDismissRequest = { showDeleteTrackDialog = null },
                    title = {
                        Text(
                            text = stringResource(R.string.you_want_delete_track),
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showDeleteTrackDialog?.let { track ->
                                viewModel.removeTrack(track, currentPlaylist.id)
                            }
                            showDeleteTrackDialog = null
                        }) {
                            Text(stringResource(R.string.yes), fontSize = 16.sp)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteTrackDialog = null }) {
                            Text(stringResource(R.string.no), fontSize = 16.sp)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}