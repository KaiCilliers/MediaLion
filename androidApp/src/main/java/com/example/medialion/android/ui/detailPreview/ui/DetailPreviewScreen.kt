package com.example.medialion.android.ui.detailPreview.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.android.ui.extensions.gradientBlue
import com.example.medialion.android.ui.search.ui.MLMediaPoster
import com.example.medialion.domain.models.SimpleMediaItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailPreviewScreen(
    mediaItem: SimpleMediaItem,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Expanded,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
        )
    )
    val scaffholdState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        modifier = modifier.height(500.dp),
        scaffoldState = scaffholdState,
        sheetContent = {
            BottomSheetContent(
                mediaItem = mediaItem,
                onAddToListClick = {}
            )
        },
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(12.dp),


        )
    {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                scope.launch {
                    if (sheetState.isCollapsed)
                        sheetState.expand()
                    else
                        sheetState.collapse()
                }
            }) {
                Text(
                    text = "Toggle sheet",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun BottomSheetContent(
    mediaItem: SimpleMediaItem,
    onAddToListClick: (movieId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .gradientBlue()
    ) {

        val (containerTop, poster, containerText, closeIcon, footer) = createRefs()

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
                    .height(150.dp)
                    .constrainAs(poster) {
                        top.linkTo(parent.top, 20.dp)
                        start.linkTo(parent.start, 20.dp)
                    },
            )

            Column(
                modifier = Modifier.constrainAs(containerText) {
                    top.linkTo(poster.top)
                    start.linkTo(poster.end)
                    end.linkTo(parent.end)
                    height = Dimension.wrapContent
                    width = Dimension.fillToConstraints
                }
            ) {

                Text(
                    text = "Harry Potter",
                    color = Color.White,
                    style = MaterialTheme.typography.h3,
                )
                Text(
                    text = "2001",
                    color = Color.White,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "It is a story about Harry Potter, an orphan brought up by his aunt and uncle " +
                            "because his parents were killed when he was a baby. Harry is unloved by his " +
                            "uncle and aunt but everything changes when he is invited to join Hogwarts " +
                            "School of Witchcraft and Wizardry and he finds out he's a wizard.",
                    color = Color.White,
                    style = MaterialTheme.typography.body1,

                    )
            }

            Image(
                painter = painterResource(id = com.example.medialion.android.R.drawable.close_icon),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = modifier
                    .size(30.dp)
                    .constrainAs(closeIcon) {
                        top.linkTo(parent.top, 20.dp)
                        end.linkTo(parent.end, 20.dp)
                    }
            )

        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .constrainAs(footer) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(containerTop.bottom)
                }
                .clickable { onAddToListClick(mediaItem.id) }
        ) {
            Image(
                painter = painterResource(id = com.example.medialion.android.R.drawable.add_to_list_icon),
                contentDescription = "",
                modifier = modifier.size(90.dp)
            )
            Spacer(modifier = modifier.height(10.dp))
            Text(
                text = "My List",
                color = Color.White,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center

            )
        }

    }
}

@Preview
@Composable
fun DetailPreviewScreenPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            DetailPreviewScreen(
                mediaItem = SimpleMediaItem(
                    id = "duis",
                    title = "This is a two line movie title",
                    posterUrl = "https://m.media-amazon.com/images/I/71QMoH7mSLL._AC_SL1500_.jpg"
                )
            )
        }
    }
}