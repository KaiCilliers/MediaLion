package com.sunrisekcdeveloper.medialion.features.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.features.search.components.MLMediaPoster
import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI
import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.utils.gradientBlue

@Composable
fun MLDetailPreviewSheet(
    mediaItem: MediaItemUI,
    onCloseClick: () -> Unit,
    onMyListClick: (SingleMediaItem) -> Unit,
    modifier: Modifier = Modifier,
) {

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .gradientBlue()
    ) {

        val (containerTop, poster, title, date, description, closeIcon, footer, heart) = createRefs()

        ConstraintLayout(
            modifier = Modifier.constrainAs(containerTop) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        ) {
            MLMediaPoster(
                posterUrl = mediaItem.posterUrl,
                title = Title(mediaItem.title),
                modifier = Modifier
                    .height(170.dp)
                    .width(100.dp)
                    .constrainAs(poster) {
                        top.linkTo(parent.top, 20.dp)
                        start.linkTo(parent.start, 20.dp)
                    },
            )

            Text(
                text = when(mediaItem.mediaType) {
                    MediaType.MOVIE -> "Movie"
                    MediaType.TV -> "TV"
                },
                style = MaterialTheme.typography.h1,
                modifier = Modifier
                    .constrainAs(heart) {
                        top.linkTo(poster.bottom)
                        start.linkTo(poster.start)
                        end.linkTo(poster.end)
                    }
            )

            Text(
                text = mediaItem.title,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.h3,
                modifier = modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top, 20.dp)
                        start.linkTo(poster.end, 16.dp)
                        end.linkTo(closeIcon.start, 16.dp)
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                text = mediaItem.releaseYear.take(4),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.h1,
                modifier = modifier
                    .constrainAs(date) {
                        top.linkTo(title.bottom, 6.dp)
                        start.linkTo(poster.end, 16.dp)
                    }
            )
            Text(
                text = mediaItem.overview,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body2,
                modifier = modifier
                    .constrainAs(description) {
                        top.linkTo(date.bottom, 6.dp)
                        start.linkTo(poster.end, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                    },
                lineHeight = 18.sp
            )


            Image(
                painter = painterResource(id = com.sunrisekcdeveloper.medialion.android.R.drawable.close_icon),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = modifier
                    .size(35.dp)
                    .constrainAs(closeIcon) {
                        top.linkTo(parent.top, 20.dp)
                        end.linkTo(parent.end, 20.dp)
                    }
                    .clickable { onCloseClick() }
            )

        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .constrainAs(footer) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(containerTop.bottom, 20.dp)
                    bottom.linkTo(parent.bottom, 20.dp)
                }
                .clickable { onMyListClick(mediaItem.toDomain()) }
        ) {
            Image(
                painter = painterResource(id = com.sunrisekcdeveloper.medialion.android.R.drawable.add_to_list_icon),
                contentDescription = "",
                modifier = modifier.size(50.dp)
            )
            Spacer(modifier = modifier.height(6.dp))
            Text(
                text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.my_list),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center

            )
        }

    }
}

@Preview
@Composable
private fun DetailPreviewScreenPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MLDetailPreviewSheet(
                mediaItem = MediaItemUI(
                    id = "partiendo",
                    title = "This is a two line movie title",
                    posterUrl = "https://image.tmdb.org/t/p/original/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg",
                    bannerUrl = "https://duckduckgo.com/?q=cum",
                    categories = listOf(),
                    overview = "ad lakda lkdjakld jaldj asklç djlajdçasjdlkasjdla dlaj kldalkdalkdajdla jsdlkajsld aj",
                    popularity = 4.5,
                    voteAverage = 6.7,
                    voteCount = 9259,
                    releaseYear = "1996",
                    mediaType = MediaType.MOVIE,
                ),
                onCloseClick = {},
                onMyListClick = {},
            )
        }
    }
}