package com.sunrisekcdeveloper.medialion.android.ui.search.ui

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
import com.sunrisekcdeveloper.medialion.MediaItemUI
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.domain.MediaType

@Composable
fun SearchIdleState(
    rowTitle: String,
    media: List<MediaItemUI>,
    onMediaClicked: (MediaItemUI) -> Unit,
    onFavoriteToggle: (mediaItem: MediaItemUI, favorited: Boolean) -> Unit,
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
                    .padding(start = 16.dp, bottom = 10.dp, top = 16.dp)
            )
        }
        items(media) { singleMovie ->
            MLMediaFavoriteListItem(
                mediaItem = singleMovie,
                onFavoriteClick = {
                    onFavoriteToggle(singleMovie, it)
                },
                modifier = Modifier.clickable { onMediaClicked(singleMovie) }
            )
        }
    }
}

@Preview
@Composable
private fun SearchIdleStatePreview() {
    MediaLionTheme {
        var movies: List<MediaItemUI> by remember {
            mutableStateOf(
                listOf(
                    MediaItemUI(id = 6664, title = "detraxit", isFavorited = false, posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", genreIds = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE),
                    MediaItemUI(id = 6664, title = "detraxit", isFavorited = false, posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", genreIds = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.TV),
                    MediaItemUI(id = 6664, title = "detraxit", isFavorited = false, posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", genreIds = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE),
                    MediaItemUI(id = 6664, title = "detraxit", isFavorited = false, posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", genreIds = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE),
                    MediaItemUI(id = 6664, title = "detraxit", isFavorited = false, posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", genreIds = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE),
                    MediaItemUI(id = 6664, title = "detraxit", isFavorited = false, posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", genreIds = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE),
                    MediaItemUI(id = 6664, title = "detraxit", isFavorited = false, posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", genreIds = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.TV),
                    MediaItemUI(id = 6664, title = "detraxit", isFavorited = false, posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", genreIds = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE),
                    MediaItemUI(id = 6664, title = "detraxit", isFavorited = false, posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", genreIds = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE),
                    MediaItemUI(id = 6664, title = "detraxit", isFavorited = false, posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", genreIds = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE),
                    MediaItemUI(id = 6664, title = "detraxit", isFavorited = false, posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", genreIds = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE),
                    MediaItemUI(id = 6664, title = "detraxit", isFavorited = false, posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", genreIds = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE),
                )
            )
        }
        Surface(modifier = Modifier.fillMaxSize()) {
            SearchIdleState(
                rowTitle = "Top Suggestions",
                media = movies,
                onMediaClicked = {},
                onFavoriteToggle = { mediaItem: MediaItemUI, favorited: Boolean ->

                    val listCopy = movies.toMutableList()
                    val mediaIndex = listCopy.indexOfFirst { it.id.toString() == mediaItem.id.toString() }
                    listCopy[mediaIndex] = listCopy[mediaIndex].copy(isFavorited = favorited)
                    listCopy.sortBy { it.id }

                    movies = listCopy
                },
            )
        }
    }
}