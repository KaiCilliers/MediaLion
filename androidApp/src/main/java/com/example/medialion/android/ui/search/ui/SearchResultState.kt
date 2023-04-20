package com.example.medialion.android.ui.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.domain.models.MovieUiModel

@Composable
fun SearchResultState(
    movies: List<MovieUiModel>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .verticalScroll(scrollState)
    ) {
        item {
            MLTitledMediaGrid(
                gridTitle = "Top Results",
                movies = movies
            )
        }
    }
}

@Preview
@Composable
fun SearchResultStatePreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SearchResultState(
                movies = listOf(
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                )
            )
        }
    }
}