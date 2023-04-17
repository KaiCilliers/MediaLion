package com.example.medialion.android.ui.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
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
    ) {
        item(span = { GridItemSpan(3) }) {
            Text(
                text = gridTitle,
                style = MaterialTheme.typography.h2,
                color = Color.White,
                modifier = modifier.padding(16.dp)
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
                    .padding(vertical = 18.dp, horizontal = 16.dp)
                    .size(height = 150.dp, width = 50.dp)
            )
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
                )
            )
        }
    }
}