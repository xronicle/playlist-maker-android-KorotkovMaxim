package com.example.playlistmaker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.models.Playlist

@Composable
fun PlaylistItemCompact(
    playlist: Playlist,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = playlist.imageUri,
            contentDescription = "Обложка",
            modifier = Modifier
                .size(45.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.LightGray),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.add_photo),
            error = painterResource(id = R.drawable.add_photo)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = playlist.name,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${playlist.tracks.size} треков",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}