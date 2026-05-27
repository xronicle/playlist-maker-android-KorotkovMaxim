package com.example.playlistmaker.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.R

val FontFamilyBase = FontFamily(
    Font(R.font.ys_display_regular, FontWeight.Normal),
    Font(R.font.ys_display_medium, FontWeight.Medium),
    Font(R.font.ys_display_bold, FontWeight.Bold),
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamilyBase,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamilyBase,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamilyBase,
        fontWeight = FontWeight.Medium,
        fontSize = 19.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamilyBase,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamilyBase,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamilyBase,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamilyBase,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamilyBase,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp
    ),
)