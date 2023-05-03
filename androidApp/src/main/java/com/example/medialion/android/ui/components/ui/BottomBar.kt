package com.example.medialion.android.ui.components.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.medialion.android.theme.MediaLionTheme

@Composable
fun BottomBar() {
    Text(text = "Hello")
}

@Preview
@Composable
private fun BottomBarPreview() {
    MediaLionTheme {
        BottomBar()
    }

}