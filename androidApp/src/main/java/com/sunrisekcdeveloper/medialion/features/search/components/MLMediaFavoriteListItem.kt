package com.sunrisekcdeveloper.medialion.features.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI
import com.sunrisekcdeveloper.medialion.oldArch.SimpleMediaItem
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType

@Composable
fun MLMediaFavoriteListItem(
    mediaItem: MediaItemUI,
    isFavorited: Boolean,
    onFavoriteClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MLMediaBanner(
            mediaItem = SimpleMediaItem(
                id = mediaItem.id,
                title = mediaItem.title,
                posterUrl = mediaItem.bannerUrl,
                mediaType = mediaItem.mediaType,
            ),
            modifier = modifier.width(180.dp),
        )
        Text(
            text = mediaItem.title,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f),
            style = MaterialTheme.typography.h4,
        )

        Image(
            painter = painterResource(id = if (isFavorited) R.drawable.heart_filled_icon else R.drawable.heart_outline_icon),
            contentDescription = "",
            modifier = modifier
                .padding(start = 8.dp)
                .size(24.dp)
                .clickable {
                    if (isFavorited) {
                        onFavoriteClick(false)
                    } else {
                        onFavoriteClick(true)
                    }
                }
        )
    }
}

@Preview
@Composable
private fun MLMediaFavoriteListItemPreview() {
    MediaLionTheme {
        val mediaItem = MediaItemUI(
            id = "asdad",
            title = "",
            posterUrl = "",
            bannerUrl = "",
            categories = listOf(),
            overview = "",
            popularity = 0.0,
            voteAverage = 0.0,
            voteCount = 0,
            releaseYear = "",
            mediaType = MediaType.MOVIE
        )

        var isFavorited by remember { mutableStateOf(true) }

        Surface(modifier = Modifier.fillMaxSize()) {
            MLMediaFavoriteListItem(
                mediaItem = mediaItem,
                onFavoriteClick = { isFavorited = it },
                isFavorited = isFavorited
            )
        }
    }
}