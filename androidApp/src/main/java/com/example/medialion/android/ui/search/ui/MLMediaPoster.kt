package com.example.medialion.android.ui.search.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.android.ui.extensions.brandGradient
import com.example.medialion.domain.models.SimpleMediaItem

@Composable
fun MLMediaPoster(
    mediaItem: SimpleMediaItem,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(0.75f, matchHeightConstraintsFirst = true)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(mediaItem.posterUrl)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp)),
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
    Box(
        modifier = modifier
            .fillMaxSize()
            .brandGradient()
    ){
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
private fun MLMediaPosterPreview() {
    MediaLionTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            MLMediaPoster(
                mediaItem = SimpleMediaItem(
                    id = "1234",
                    title = "Harry Potter and the Philosopher's stone",
                    posterUrl = "https://m.media-amazon.com/images/I/71QMoH7mSLL._AC_SL1500_.jpgx"
                ),
                modifier = Modifier.height(200.dp),
            )
        }
    }
}