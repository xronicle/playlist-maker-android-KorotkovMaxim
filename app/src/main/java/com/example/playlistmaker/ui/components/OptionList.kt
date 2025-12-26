package com.example.playlistmaker.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
data class ListItem(
    val iconPainter: Painter,
    val iconDescription: String,
    val text: String,
    val onClick: () -> Unit
)

@Composable
fun List(vararg items: ListItem) {
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
        Column(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    ),
                )
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(vertical = 28.dp, horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items.forEach {
                ListButton(
                    it.iconPainter,
                    it.iconDescription,
                    it.text,
                    it.onClick
                )
            }
        }
    }
}

@Composable
private fun ListButton(iconPainter: Painter, iconDescription: String, text: String, onClick: () -> Unit) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            }
            .padding(vertical = 10.dp, horizontal = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                iconPainter,
                iconDescription,
                modifier = Modifier
                    .size(24.dp)
                    .aspectRatio(1f),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Icon(
            painterResource(R.drawable.arrow_right),
            contentDescription = "right arrow",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}