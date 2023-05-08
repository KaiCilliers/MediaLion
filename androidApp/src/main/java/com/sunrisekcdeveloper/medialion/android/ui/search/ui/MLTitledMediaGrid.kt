package com.sunrisekcdeveloper.medialion.android.ui.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.MediaItemUI
import com.sunrisekcdeveloper.medialion.R
import com.sunrisekcdeveloper.medialion.SimpleMediaItem
import com.sunrisekcdeveloper.medialion.TitledMedia
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.domain.MediaType

@Composable
fun MLTitledMediaGrid(
    gridTitle: String,
    media: List<MediaItemUI>,
    suggestedMedia: List<TitledMedia>,
    onMediaClicked: (MediaItemUI) -> Unit,
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
        items(media) { singleMovie ->
            MLMediaPoster(
                mediaItem = SimpleMediaItem(
                    id = singleMovie.id.toString(),
                    title = singleMovie.title,
                    posterUrl = singleMovie.posterUrl,
                    mediaType = singleMovie.mediaType,
                ),
                modifier = Modifier.clickable { onMediaClicked(singleMovie) }
            )
        }
        items(suggestedMedia, span = { GridItemSpan(3) }) {
            if (it.content.isNotEmpty()) {
                MLTitledMediaRow(
                    rowTitle = it.title,
                    media = it.content,
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
                gridTitle = stringResource(id = R.string.top_results),
                media = (1..20).map { MediaItemUI(
                    id = 9201,
                    title = "luptatum",
                    isFavorited = false,
                    posterUrl = "http://www.bing.com/search?q=nonumes",
                    bannerUrl = "https://duckduckgo.com/?q=donec",
                    genreIds = listOf(),
                    overview = "maximus",
                    popularity = 28.29,
                    voteAverage = 30.31,
                    voteCount = 3879,
                    releaseYear = "pretium",
                    mediaType = MediaType.MOVIE,
                ) }.toList(),
                suggestedMedia = (1..3).map {
                    TitledMedia(
                        title = "Suggested Heading #$it",
                        content = (1..20).map {
                            MediaItemUI(
                                id = 7969,
                                title = "errem",
                                isFavorited = false,
                                posterUrl = "https://duckduckgo.com/?q=per",
                                bannerUrl = "http://www.bing.com/search?q=viderer",
                                genreIds = listOf(),
                                overview = "oratio",
                                popularity = 36.37,
                                voteAverage = 38.39,
                                voteCount = 2534,
                                releaseYear = "vel",
                                mediaType = MediaType.MOVIE,
                            )
                        }
                            .toList()
                    )
                },
                onMediaClicked = {},
            )
        }
    }
}