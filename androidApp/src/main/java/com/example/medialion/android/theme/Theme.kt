package com.example.medialion.android.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.medialion.ColorRes

@Composable
fun MediaLionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            background = colorResource(id = ColorRes.background.resourceId),
            primary = colorResource(id = ColorRes.primary.resourceId),
            primaryVariant = colorResource(id = ColorRes.primaryVariant.resourceId),
            secondary = colorResource(id = ColorRes.secondary.resourceId)
        )
    } else {
        lightColors(
            background = colorResource(id = ColorRes.background.resourceId),
            primary = colorResource(id = ColorRes.primary.resourceId),
            primaryVariant = colorResource(id = ColorRes.primaryVariant.resourceId),
            secondary = colorResource(id = ColorRes.secondary.resourceId),
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