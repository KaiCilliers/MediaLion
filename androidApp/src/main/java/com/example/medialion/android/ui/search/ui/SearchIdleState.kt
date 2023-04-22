package com.example.medialion.android.ui.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.domain.models.MovieUiModel

@Composable
fun SearchIdleState(
    rowTitle: String,
    movies: List<MovieUiModel>,
    onMediaClicked: (MovieUiModel) -> Unit,
    onFavoriteToggle: (mediaId: String, favorited: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier.background(MaterialTheme.colors.background)
    ) {
        item {
            Text(
                text = rowTitle,
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.secondary,
                modifier = modifier
                    .padding(start = 16.dp, bottom = 16.dp)
            )
        }
        items(movies) { singleMovie ->
            MLMediaFavoriteListItem(
                movie = singleMovie,
                onFavoriteClick = {
                    onFavoriteToggle(singleMovie.id.toString(), it)
                },
                modifier = Modifier.clickable { onMediaClicked(singleMovie) }
            )
        }
    }
}

@Preview
@Composable
fun SearchIdleStatePreview() {
    MediaLionTheme {
        var movies: List<MovieUiModel> by remember {
            mutableStateOf(
                listOf(
                    MovieUiModel(1, "HP", false),
                    MovieUiModel(2, "HP", false),
                    MovieUiModel(3, "HP", false),
                    MovieUiModel(4, "HP", false),
                    MovieUiModel(5, "HP", false),
                    MovieUiModel(6, "HP", false),
                    MovieUiModel(7, "HP", false),
                    MovieUiModel(8, "HP", false),
                    MovieUiModel(9, "HP", false),
                    MovieUiModel(10, "HP", false),
                    MovieUiModel(11, "HP", false),
                    MovieUiModel(12, "HP", false),
                )
            )
        }
        Surface(modifier = Modifier.fillMaxSize()) {
            SearchIdleState(
                rowTitle = "Top Suggestions",
                movies = movies,
                onMediaClicked = {},
                onFavoriteToggle = { mediaId: String, favorited: Boolean ->

                    val listCopy = movies.toMutableList()
                    val mediaIndex = listCopy.indexOfFirst { it.id.toString() == mediaId }
                    listCopy[mediaIndex] = listCopy[mediaIndex].copy(isFavorited = favorited)
                    listCopy.sortBy { it.id }

                    movies = listCopy
                },
            )
        }
    }
}