package com.example.medialion.android.ui.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medialion.android.R
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.domain.models.MovieUiModel
import com.example.medialion.domain.models.SimpleMediaItem

@Composable
fun MLMediaFavoriteListItem(
    movie: MovieUiModel,
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
                id = movie.id.toString(),
                title = movie.title,
                posterUrl = movie.bannerUrl,
            ),
            modifier = modifier.width(180.dp),
        )
        Text(
            text = movie.title,
            color = Color.White,
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f),
            style = MaterialTheme.typography.subtitle1,
        )

        Image(
            painter = painterResource(id = if (movie.isFavorited) R.drawable.heart_filled_icon else R.drawable.heart_outline_icon),
            contentDescription = "",
            modifier = modifier
                .padding(start = 8.dp)
                .size(24.dp)
                .clickable {
                    if (movie.isFavorited) {
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
        var movie: MovieUiModel by remember {
            mutableStateOf(MovieUiModel(1, "This is a long movie title", false))
        }

        Surface(modifier = Modifier.fillMaxSize()) {
            MLMediaFavoriteListItem(
                movie = movie,
                onFavoriteClick = {
                    movie = movie.copy(isFavorited = it)
                },
            )
        }
    }
}