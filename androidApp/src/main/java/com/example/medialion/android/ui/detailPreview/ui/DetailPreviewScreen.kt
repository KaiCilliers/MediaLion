package com.example.medialion.android.ui.detailPreview.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.medialion.ColorRes
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.android.ui.extensions.gradientBackground
import com.example.medialion.android.ui.search.ui.MLMediaPoster
import com.example.medialion.domain.components.detailPreview.DetailPreviewState
import com.example.medialion.domain.models.MovieUiModel
import com.example.medialion.domain.models.SimpleMediaItem

@Composable
fun DetailPreviewScreen(
    state: DetailPreviewState,
    modifier: Modifier = Modifier
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
            .gradientBackground(
                colors = listOf(
                    colorResource(id = ColorRes.primary.resourceId),
                    colorResource(id = ColorRes.primaryVariant.resourceId),
                ),
                angle = -90f
            )
    ) {

        val (poster, detailsColumn, ctaButton, closeIcon) = createRefs()

        when (state) {
            is DetailPreviewState.Details -> {
                Row {
                    MLMediaPoster(
                        mediaItem = SimpleMediaItem(
                            id = state.media.id.toString(),
                            title = state.media.title,
                            posterUrl = state.media.posterUrl
                        ),
                        modifier = modifier
                            .height(100.dp)
                            .width(80.dp)
                    )
                    Column {
                        Text(
                            text = state.media.title,
                            color = Color.White,
                            fontSize = 42.sp
                        )
                        Text(
                            text = "2021",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "Filled with meme-able moments, this warmhearted, critically acclaimed workplace comedy serves up office escapades and takes on topical issues.",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailPreviewScreenPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            DetailPreviewScreen(
                state = DetailPreviewState.Details(
                    media = MovieUiModel(
                        id = 1541,
                        title = "luctus",
                        isFavorited = false,
                        posterUrl = "https://m.media-amazon.com/images/I/71QMoH7mSLL._AC_SL1500_.jpg"
                    )
                )
            )
        }
    }
}