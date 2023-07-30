package com.sunrisekcdeveloper.medialion.features.search.components

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
import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI
import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType
import com.sunrisekcdeveloper.medialion.theme.MediaLionTheme

@Composable
fun TopSuggestions(
    rowTitle: String,
    mediaWithFavorites: List<Pair<MediaItemUI, Boolean>>,
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
        items(mediaWithFavorites) { mediaWithFav ->
            MLMediaFavoriteListItem(
                mediaItem = mediaWithFav.first,
                onFavoriteClick = { onFavoriteToggle(mediaWithFav.first, it) },
                isFavorited = mediaWithFav.second,
                modifier = Modifier.clickable { onMediaClicked(mediaWithFav.first) }
            )
        }
    }
}

@Preview
@Composable
private fun TopSuggestionsPreview() {
    MediaLionTheme {
        var moviesWithFav: List<Pair<MediaItemUI, Boolean>> by remember {
            mutableStateOf(
                listOf(
                    Pair(MediaItemUI(id = "66642", title = "detraxit", posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", categories = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.TV), false),
                    Pair(MediaItemUI(id = "66641", title = "detraxit", posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", categories = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE), false),
                    Pair(MediaItemUI(id = "66643", title = "detraxit", posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", categories = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE), false),
                    Pair(MediaItemUI(id = "66644", title = "detraxit", posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", categories = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE), false),
                    Pair(MediaItemUI(id = "66645", title = "detraxit", posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", categories = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE), false),
                    Pair(MediaItemUI(id = "66646", title = "detraxit", posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", categories = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE), false),
                    Pair(MediaItemUI(id = "66647", title = "detraxit", posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", categories = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.TV), false),
                    Pair(MediaItemUI(id = "66648", title = "detraxit", posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", categories = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE), false),
                    Pair(MediaItemUI(id = "66649", title = "detraxit", posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", categories = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE), false),
                    Pair(MediaItemUI(id = "666411", title = "detraxit", posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", categories = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE), false),
                    Pair(MediaItemUI(id = "666111", title = "detraxit", posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", categories = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE), false),
                    Pair(MediaItemUI(id = "22264", title = "detraxit", posterUrl = "https://search.yahoo.com/search?p=noster", bannerUrl = "http://www.bing.com/search?q=lacinia", categories = listOf(), overview = "electram", popularity = 20.21, voteAverage = 22.23, voteCount = 5474, releaseYear = "mentitum", mediaType = MediaType.MOVIE), false),
                )
            )
        }
        Surface(modifier = Modifier.fillMaxSize()) {
            TopSuggestions(
                rowTitle = "Top Suggestions",
                mediaWithFavorites = moviesWithFav,
                onMediaClicked = {},
                onFavoriteToggle = { mediaItem: MediaItemUI, favorited: Boolean ->

                    val listCopy = moviesWithFav.toMutableList()
                    val mediaIndex = listCopy.indexOfFirst { it.first.id == mediaItem.id }
                    listCopy[mediaIndex] = Pair(listCopy[mediaIndex].first, favorited)
                    listCopy.sortBy { it.first.id }

                    moviesWithFav = listCopy
                },
            )
        }
    }
}