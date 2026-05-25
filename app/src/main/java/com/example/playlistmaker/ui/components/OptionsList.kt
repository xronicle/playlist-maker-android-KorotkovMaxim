package com.example.playlistmaker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

data class ListItem(
    val leadingIcon: (@Composable () -> Unit)? = null,
    val trailingIcon: (@Composable () -> Unit)? = null,
    val content: @Composable () -> Unit,
    val shape: RoundedCornerShape = RoundedCornerShape(0.dp),
    val onClick: (() -> Unit)? = null
)

@Composable
fun OptionsList(
    isLazy: Boolean = false,
    shape: RoundedCornerShape = RoundedCornerShape(0.dp),
    paddings: PaddingValues = PaddingValues(0.dp),
    items: List<ListItem>,
    itemsPaddings: PaddingValues = PaddingValues(0.dp)
) {
    if (isLazy) {
        LazyColumn(
            modifier = Modifier
                .clip(
                    shape = shape
                )
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(paddings),
        ) {
            items(items.size) { index ->
                ListButton(
                    items[index].leadingIcon,
                    items[index].trailingIcon,
                    items[index].content,
                    items[index].shape,
                    itemsPaddings,
                    items[index].onClick
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .clip(
                    shape = shape
                )
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(paddings),
        ) {
            items.forEach {
                ListButton(
                    it.leadingIcon,
                    it.trailingIcon,
                    it.content,
                    it.shape,
                    itemsPaddings,
                    it.onClick
                )
            }
        }
    }

}


@Composable
private fun ListButton(
    leadingIcon: (@Composable () -> Unit)?,
    trailingIcon: (@Composable () -> Unit)?,
    content: @Composable () -> Unit,
    shape: RoundedCornerShape,
    paddings: PaddingValues,
    onClick: (() -> Unit)?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable {
                onClick?.invoke()
            }
            .padding(paddings),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leadingIcon?.let {
            it()
            Spacer(modifier = Modifier.width(8.dp))
        }
        Box(modifier = Modifier.weight(1f)) {
            content.invoke()
        }
        trailingIcon?.invoke()
    }
}