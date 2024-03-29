package com.sunrisekcdeveloper.medialion.features.search.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI
import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.theme.MediaLionTheme

@Composable
fun MLTitledMediaRow(
    rowTitle: String,
    media: List<MediaItemUI>,
    onMediaItemClicked: (MediaItemUI) -> Unit,
    modifier: Modifier = Modifier,
    onTitleClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
            Text(
                text = rowTitle,
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.secondary,
                modifier = modifier
                    .padding(bottom = 20.dp, top = 6.dp, start = 6.dp)
                    .clickable { onTitleClicked() }
            )
        LazyRow(
            modifier = modifier
                .background(MaterialTheme.colors.background)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            userScrollEnabled = true
        ) {
            if (media.isEmpty()) {
                items(1) {
                    MLMediaPoster(
                        title = Title("No title"),
                        posterUrl = null,
                        modifier = modifier
                            // todo replace hardcoded size
                            .size(height = 143.dp, width = 92.dp)
                    )
                }
            }

            items(media) { singleMovie ->
                MLMediaPoster(
                    posterUrl = singleMovie.posterUrl,
                    title = Title(singleMovie.title),
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
                media = listOf(
                    MediaItemUI(id = "6382", title = "ignota",  posterUrl = "https://www.google.com/#q=aenean", bannerUrl = "https://duckduckgo.com/?q=lectus", categories = listOf(), overview = "quis", popularity = 44.45, voteAverage = 46.47, voteCount = 8908, releaseYear = "blandit", mediaType = MediaType.MOVIE),
                    MediaItemUI(id = "6383", title = "ignota",  posterUrl = "https://www.google.com/#q=aenean", bannerUrl = "https://duckduckgo.com/?q=lectus", categories = listOf(), overview = "quis", popularity = 44.45, voteAverage = 46.47, voteCount = 8908, releaseYear = "blandit", mediaType = MediaType.TV),
                    MediaItemUI(id = "6384", title = "ignota",  posterUrl = "https://www.google.com/#q=aenean", bannerUrl = "https://duckduckgo.com/?q=lectus", categories = listOf(), overview = "quis", popularity = 44.45, voteAverage = 46.47, voteCount = 8908, releaseYear = "blandit", mediaType = MediaType.MOVIE),
                    MediaItemUI(id = "6381", title = "ignota",  posterUrl = "https://www.google.com/#q=aenean", bannerUrl = "https://duckduckgo.com/?q=lectus", categories = listOf(), overview = "quis", popularity = 44.45, voteAverage = 46.47, voteCount = 8908, releaseYear = "blandit", mediaType = MediaType.MOVIE),
                ),
                onMediaItemClicked = {},
                onTitleClicked = {})
        }
    }
}