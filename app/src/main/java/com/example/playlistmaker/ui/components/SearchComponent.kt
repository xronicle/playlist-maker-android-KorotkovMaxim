package com.example.playlistmaker.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme // Добавлен импорт для темы
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.playlistmaker.domain.models.Track

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackListItem(
    track: Track,
    onLongClick: (() -> Unit)? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick?.invoke() }
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        SubcomposeAsyncImage(
            model = track.image,
            contentDescription = "Обложка",
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop,

            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF333333)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Audiotrack,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            error = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF333333)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Audiotrack,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = track.trackName,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = track.artistName,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )
                Text(
                    text = " • ${track.trackTime}",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun HistoryRequests(
    historyList: List<String>,
    onClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 200.dp)
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp))
    ) {
        items(historyList.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(historyList[index]) }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.History,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = historyList[index],
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            if (historyList[index] != historyList.last()) {
                HorizontalDivider(thickness = 0.5.dp)
            }
        }
    }
}