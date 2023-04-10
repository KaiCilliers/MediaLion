package com.example.medialion.android.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val small: Dp = 4.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val xLarge: Dp = 64.dp,
)
val LocalSpacing = compositionLocalOf { Spacing() }
val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
