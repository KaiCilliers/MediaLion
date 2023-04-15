package com.example.medialion.android.ui.search.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.domain.models.MovieUiModel

@Composable
fun MLTitledMediaGrid(
    gridTitle: String,
    movies: List<MovieUiModel>
) {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        item(span = { GridItemSpan(3) }) {
            Text(text = gridTitle)
        }
        items(movies) { singleMovie ->
            Text(text = singleMovie.title)
        }
    }
}

@Preview
@Composable
fun MLTitledMediaGridPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MLTitledMediaGrid(
                gridTitle = "Title #1",
                movies = listOf(
                    MovieUiModel(1, "sdasdalkd", true),
                    MovieUiModel(1, "sdasdalkd", true),
                    MovieUiModel(1, "sdasdalkd", true),
                    MovieUiModel(1, "sdasdalkd", true),
                    MovieUiModel(1, "sdasdalkd", true),
                )
            )
        }
    }
}