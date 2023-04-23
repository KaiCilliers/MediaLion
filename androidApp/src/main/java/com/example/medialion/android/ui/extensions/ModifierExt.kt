package com.example.medialion.android.ui.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.medialion.ColorRes
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun Modifier.gradientBackground(colors: List<Color>, angle: Float) = this.then(
    Modifier.drawBehind {
        val angleRad = angle / 180f * Math.PI
        val x = cos(angleRad).toFloat() //Fractional x
        val y = sin(angleRad).toFloat() //Fractional y

        val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
        val offset = center + Offset(x * radius, y * radius)

        val exactOffset = Offset(
            x = offset.x.coerceAtLeast(0f).coerceAtMost(size.width),
            y = size.height - offset.y.coerceAtLeast(0f).coerceAtMost(size.height)
        )

        drawRect(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(size.width, size.height) - exactOffset,
                end = exactOffset
            ),
            size = size
        )
    }
)

fun Modifier.gradientOrange() = composed {
    this.then(
        Modifier.gradientBackground(
            colors = listOf(
                colorResource(id = ColorRes.primary.resourceId),
                colorResource(id = ColorRes.primaryVariant.resourceId)
            ),
            angle = 60f
        )
    )
}

fun Modifier.gradientBlue() = composed {
    this.then(
        Modifier.gradientBackground(
            colors = listOf(
                colorResource(id = ColorRes.primary.resourceId),
                colorResource(id = ColorRes.primaryVariantBlue.resourceId)
            ),
            angle = -90f
        )
    )
}