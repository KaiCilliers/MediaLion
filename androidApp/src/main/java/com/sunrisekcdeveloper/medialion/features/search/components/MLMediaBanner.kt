package com.sunrisekcdeveloper.medialion.features.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.sunrisekcdeveloper.medialion.components.shared.data.models.TMDBImageUrl
import com.sunrisekcdeveloper.medialion.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.utils.debug
import com.sunrisekcdeveloper.medialion.utils.gradientOrange

@Composable
fun MLMediaBanner(
    title: String,
    bannerUrl: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(1.8f)
    ) {
        debug {TMDBImageUrl(bannerUrl).toString() }
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(TMDBImageUrl(bannerUrl).toString())
                .crossfade(true)
                .build(),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .fillMaxSize(),
            contentDescription = null,
            loading = { PosterPlaceholder(mediaTitle = title) },
            error = {
                // todo log logs here about posters not loading
                PosterPlaceholder(mediaTitle = title) },
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
            .clip(RoundedCornerShape(4.dp))
            .fillMaxSize()
            .gradientOrange()
    ){
        Text(
            text = mediaTitle,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondary,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun MLMediaBannerPreview() {
    MediaLionTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            MLMediaBanner(
                title = "Harry Potter and the Philosopher's stone",
                bannerUrl =  "https://image.tmdb.org/t/p/original/hziiv14OpD73u9gAak4XDDfBKa2.jpg",
                modifier = Modifier.width(200.dp),
            )
        }
    }
}