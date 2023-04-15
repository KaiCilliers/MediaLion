package com.example.medialion.android.ui.detailPreview.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.medialion.android.theme.MediaLionTheme

@Composable
fun DetailPreviewScreen() {
    Text(
        text = "DetailPreviewScreen",
        fontSize = 42.sp
    )
}

@Preview
@Composable
fun DetailPreviewScreenPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            DetailPreviewScreen()
        }
    }
}