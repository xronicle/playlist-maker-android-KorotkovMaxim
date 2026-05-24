package com.example.playlistmaker.ui.screens.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.ui.screens.playlists.PlaylistsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailsScreen(
    track: Track,
    playlistsViewModel: PlaylistsViewModel,
    navigateBack: () -> Unit
) {
    val trackState by playlistsViewModel.getTrackState(track).collectAsState(initial = track)
    val playlists by playlistsViewModel.playlists.collectAsState(initial = emptyList())
    var showBottomSheet by remember { mutableStateOf(false) }
    val displayTrack = trackState ?: track
    var isFavorite by remember(displayTrack.favorite) { mutableStateOf(displayTrack.favorite) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Назад",
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navigateBack() },
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            AsyncImage(
                model = displayTrack.image.replaceAfterLast('/', "512x512bb.jpg"),
                contentDescription = "Обложка",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(vertical = 24.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = displayTrack.trackName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = displayTrack.artistName,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "В плейлист",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { showBottomSheet = true },
                    tint = MaterialTheme.colorScheme.onBackground
                )

                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Лайк",
                    tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            isFavorite = !isFavorite
                            playlistsViewModel.toggleFavorite(displayTrack, isFavorite)
                        }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Длительность", color = Color.Gray, fontSize = 14.sp)
                Text(
                    text = displayTrack.trackTime,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            if (playlists.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "У вас пока нет плейлистов",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                ) {
                    items(playlists) { playlist ->
                        Text(
                            text = playlist.name,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    playlistsViewModel.insertTrackToPlaylist(displayTrack, playlist.id)
                                    showBottomSheet = false
                                }
                                .padding(horizontal = 24.dp, vertical = 16.dp)
                        )
                    }
                }
            }
        }
    }
}