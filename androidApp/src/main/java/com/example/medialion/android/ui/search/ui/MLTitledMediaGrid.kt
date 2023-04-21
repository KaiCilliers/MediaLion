package com.example.medialion.android.ui.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.domain.models.MovieUiModel
import com.example.medialion.domain.models.SimpleMediaItem

@Composable
fun MLTitledMediaGrid(
    gridTitle: String,
    movies: List<MovieUiModel>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize(),
        contentPadding = PaddingValues(22.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)

    ) {
        item(span = { GridItemSpan(3) }) {
            Text(
                text = gridTitle,
                style = MaterialTheme.typography.h3,
                color = Color.White,
                modifier = modifier.padding(top = 16.dp, bottom = 4.dp),

            )
        }
        items(movies) { singleMovie ->
            MLMediaPoster(
                mediaItem = SimpleMediaItem(
                    id = singleMovie.id.toString(),
                    title = singleMovie.title,
                    posterUrl = singleMovie.posterUrl
                ),
                modifier = modifier
                    .size(height = 130.dp, width = 50.dp)
            )
        }
        item(span = { GridItemSpan(3) }) {
            MLTitledMediaRow(rowTitle = "Related Movies Titles", movies = movies)
        }
        item(span = { GridItemSpan(3) }) {
            MLTitledMediaRow(rowTitle = "Related Series Titles", movies = movies)
        }
        item(span = { GridItemSpan(3) }) {
            MLTitledMediaRow(rowTitle = "Related Documentary Titles", movies = movies)
        }
    }
}

@Preview
@Composable
fun MLTitledMediaGridPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MLTitledMediaGrid(
                gridTitle = "Top Results",
                movies = listOf(
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
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