package com.example.medialion.android.ui.discovery.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.medialion.android.theme.MediaLionTheme

@Composable
fun CategoriesDialog() {
    Text(text = "Hello")
}

@Preview
@Composable
private fun CategoriesDialogPreview() {
    MediaLionTheme {
        CategoriesDialog()
    }

}