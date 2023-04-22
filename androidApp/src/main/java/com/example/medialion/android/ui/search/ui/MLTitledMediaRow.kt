package com.example.medialion.android.ui.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.domain.models.MovieUiModel
import com.example.medialion.domain.models.SimpleMediaItem

@Composable
fun MLTitledMediaRow(
    rowTitle: String,
    movies: List<MovieUiModel>,
    onMediaItemClicked: (MovieUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
            Text(
                text = rowTitle,
                style = MaterialTheme.typography.subtitle1,
                color = Color.White,
                modifier = modifier.padding(bottom = 20.dp)
            )
        LazyRow(
            modifier = modifier
                .background(MaterialTheme.colors.background)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            userScrollEnabled = true
        ) {

            items(movies) {singleMovie ->
                MLMediaPoster(   mediaItem = SimpleMediaItem(
                    id = singleMovie.id.toString(),
                    title = singleMovie.title,
                    posterUrl = singleMovie.posterUrl
                ),
                    modifier = modifier
                        // todo replace hardcoded size
                        .size(height = 143.dp, width = 92.dp)
                        .clickable { onMediaItemClicked(singleMovie) }
                )
        }
            }
    }


}

@Preview
@Composable
private fun MLTitledMediaRowPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MLTitledMediaRow(
                rowTitle = "Related Movie Titles",
                movies = listOf(
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                    MovieUiModel(1, "HP", true),
                ),
                onMediaItemClicked = {})
        }
    }
}