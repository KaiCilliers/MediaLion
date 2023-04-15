package com.example.medialion.android.ui.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.medialion.android.R
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.models.MovieUiModel

@Composable
fun MLMediaFavoriteListItem(
    movie: MovieUiModel,
    onFavoriteClick: (Boolean) -> Unit,
) {
    Column {
        Text(
            text = "MLMediaFavoriteListItem",
            fontSize = 42.sp,
        )
        Image(
            painter = painterResource(id = if (movie.isFavorited) R.drawable.heart_filled_icon else R.drawable.heart_outline_icon),
            contentDescription = "",
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.clickable {
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
fun MLMediaFavoriteListItemPreview() {
    MediaLionTheme {
        var movie: MovieUiModel by remember {
            mutableStateOf(MovieUiModel(1, "Kai", false))
        }

        Surface(modifier = Modifier.fillMaxSize()) {
            MLMediaFavoriteListItem(
                movie = movie,
                onFavoriteClick = {
                    movie = movie.copy(isFavorited = it)
                }
            )
        }
    }
}