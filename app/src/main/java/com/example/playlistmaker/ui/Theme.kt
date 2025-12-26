package com.example.playlistmaker.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val DarkColorScheme = darkColorScheme(
    primary = Blue,
    onPrimary = Light,
    secondary = Dark,
    onSecondary = Light,
    background = Dark,
    onBackground = Light,
    surface = Dark,
    surfaceContainer = Light,
    onSurface = Dark,
    onSurfaceVariant = Dark,
    error = Red,
    outline = Gray,
)

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    onPrimary = Light,
    secondary = Light,
    onSecondary = Gray,
    background = Light,
    onBackground = Dark,
    surface = Light,
    surfaceContainer = LightGray,
    onSurface = Dark,
    onSurfaceVariant = Gray,
    error = Red,
    outline = Gray
)

@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}