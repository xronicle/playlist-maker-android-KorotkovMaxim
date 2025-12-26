package com.example.playlistmaker.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val DarkColorScheme = darkColorScheme(
    primary = Blue,
    onPrimary = Light,
    secondary = BlueAlt,
    onSecondary = Dark,
    background = Dark,
    onBackground = Light,
    surface = Dark,
    onSurface = Light,
    error = Red,
    outline = Gray,
)

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    onPrimary = Light,
    secondary = BlueAlt,
    onSecondary = Light,
    background = Light,
    onBackground = Dark,
    surface = Light,
    onSurface = Gray,
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