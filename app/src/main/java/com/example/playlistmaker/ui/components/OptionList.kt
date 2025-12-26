package com.example.playlistmaker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

data class ListItem(
    val leadingIcon: (@Composable () -> Unit)? = null,
    val trailingIcon: (@Composable () -> Unit)? = null,
    val text: String,
    val shape: RoundedCornerShape? = null,
    val onClick: (() -> Unit)? = null
)

@Composable
fun List(
    textStyle: TextStyle,
    shape: RoundedCornerShape? = null,
    paddings: PaddingValues,
    items: List<ListItem>
) {
    Column(
        modifier = Modifier
            .clip(
                shape = shape ?: RoundedCornerShape(0.dp)
            )
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(paddings),
    ) {
        items.forEach {
            ListButton(
                it.leadingIcon,
                it.trailingIcon,
                it.text,
                textStyle,
                it.shape,
                it.onClick
            )
        }
    }
}

@Composable
private fun ListButton(
    leadingIcon: (@Composable () -> Unit)?,
    trailingIcon: (@Composable () -> Unit)?,
    text: String,
    textStyle: TextStyle,
    shape: RoundedCornerShape?,
    onClick: (() -> Unit)?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape ?: RoundedCornerShape(0.dp))
            .clickable {
                onClick?.invoke()
            }
            .padding(vertical = 20.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            leadingIcon?.invoke()
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onBackground,
                style = textStyle
            )
        }
        trailingIcon?.invoke()
    }
}