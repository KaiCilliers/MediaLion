package com.example.medialion.android.ui.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.medialion.android.R
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.domain.models.MovieUiModel
import com.example.medialion.domain.models.SimpleMediaItem

@Composable
fun MLTitledMediaGrid(
    gridTitle: String,
    movies: List<MovieUiModel>,
    suggestedMedia: List<Pair<String, List<MovieUiModel>>>,
    onMediaClicked: (MovieUiModel) -> Unit,
    modifier: Modifier = Modifier,
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
                color = MaterialTheme.colors.secondary,
                modifier = modifier.padding(top = 8.dp, bottom = 6.dp),

                )
        }
        items(movies) { singleMovie ->
            MLMediaPoster(
                mediaItem = SimpleMediaItem(
                    id = singleMovie.id.toString(),
                    title = singleMovie.title,
                    posterUrl = singleMovie.posterUrl
                ),
                modifier = Modifier.clickable { onMediaClicked(singleMovie) }
            )
        }
        items(suggestedMedia, span = { GridItemSpan(3) }) {
            if (it.second.isNotEmpty()) {
                MLTitledMediaRow(
                    rowTitle = it.first,
                    movies = it.second,
                    onMediaItemClicked = { onMediaClicked(it) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun MLTitledMediaGridPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MLTitledMediaGrid(
                gridTitle = stringResource(id = com.example.medialion.R.string.top_results),
                movies = (1..20).map { MovieUiModel(1, "HP", true) }.toList(),
                suggestedMedia = (1..3).map {
                    "Suggested Heading #$it" to (1..20).map { MovieUiModel(1, "Movie #$it", true) }
                        .toList()
                },
                onMediaClicked = {}
            )
        }
    }
}