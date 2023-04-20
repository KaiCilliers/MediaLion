package com.example.medialion.android.ui.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.medialion.ColorRes
import com.example.medialion.R
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.android.ui.extensions.gradientBackground
import com.example.medialion.domain.models.SimpleMediaItem

@Composable
fun MLMediaPoster(
    mediaItem: SimpleMediaItem,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(mediaItem.posterUrl)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .fillMaxSize(),
            contentDescription = null,
            loading = { PosterPlaceholder(mediaTitle = mediaItem.title)},
            error = { PosterPlaceholder(mediaTitle = mediaItem.title) },
        )
    }
}

@Composable
private fun PosterPlaceholder(
    mediaTitle: String,
    modifier: Modifier = Modifier
) {
    Box{
        Image(
            painter = painterResource(id = com.example.medialion.android.R.drawable.grid_placeholder),
            contentDescription = "",
            )
        Text(
            text = mediaTitle,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 18.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun MLMediaPosterPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MLMediaPoster(
                mediaItem = SimpleMediaItem(
                    id = "1234",
                    title = "Harry Potter and the Philosopher's stone",
                    posterUrl = "https://m.media-amazon.com/images/I/71QMoH7mSLL._AC_SL1500_.jpg"
                )
            )
        }
    }
}