package com.example.medialion.android.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

@Composable
fun MediaLionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            background = Color(0xFF0d2028),
            primary = Color(0xFF003658),
            secondary = Color(0xFFFFFFFF)
        )
    } else {
        lightColors(
            background = Color(0xFF0d2028),
            primary = Color(0xFF003658),
            secondary = Color(0xFFFFFFFF)
        )
    }
    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}