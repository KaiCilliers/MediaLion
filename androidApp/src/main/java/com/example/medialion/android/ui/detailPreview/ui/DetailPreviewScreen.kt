package com.example.medialion.android.ui.detailPreview.ui

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.medialion.android.R
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.android.ui.extensions.gradientBlue
import com.example.medialion.android.ui.saveToCollection.ui.SaveToCollectionScreen
import com.example.medialion.android.ui.search.ui.MLMediaPoster
import com.example.medialion.domain.models.SimpleMediaItem

@Composable
fun DetailPreviewScreen(
    mediaItem: SimpleMediaItem,
    onCloseClick: () -> Unit,
    onMyListClick: (SimpleMediaItem) -> Unit,
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .gradientBlue()
    ) {

        val (containerTop, poster, title, date, description, closeIcon, footer) = createRefs()

        ConstraintLayout(
            modifier = Modifier.constrainAs(containerTop) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        ) {
            MLMediaPoster(
                mediaItem = mediaItem,
                modifier = Modifier
                    .height(170.dp)
                    .width(100.dp)
                    .constrainAs(poster) {
                        top.linkTo(parent.top, 20.dp)
                        start.linkTo(parent.start, 20.dp)
                    },
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
                text = "2001",
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.h1,
                modifier = modifier
                    .constrainAs(date) {
                        top.linkTo(title.bottom, 6.dp)
                        start.linkTo(poster.end, 16.dp)
                    }
            )
            Text(
                text = "It is a story about Harry Potter, an orphan brought up by his aunt and uncle " +
                        "because his parents were killed when he was a baby. Harry is unloved by his " +
                        "uncle and aunt but everything changes when he is invited to join Hogwarts " +
                        "School of Witchcraft and Wizardry and he finds out he's a wizard.",
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
                painter = painterResource(id = com.example.medialion.android.R.drawable.close_icon),
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
                .clickable { onMyListClick(mediaItem) }
        ) {
            Image(
                painter = painterResource(id = com.example.medialion.android.R.drawable.add_to_list_icon),
                contentDescription = "",
                modifier = modifier.size(50.dp)
            )
            Spacer(modifier = modifier.height(6.dp))
            Text(
                text = stringResource(id = com.example.medialion.R.string.my_list),
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
            DetailPreviewScreen(
                mediaItem = SimpleMediaItem(
                    id = "duis",
                    title = "This is a two line movie title",
                    posterUrl = "https://image.tmdb.org/t/p/original/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg"
                ),
                onCloseClick = {},
                onMyListClick = {},
            )
        }
    }
}