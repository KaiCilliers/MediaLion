package com.example.medialion.android.ui.collections.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.medialion.MediaItemUI
import com.example.medialion.android.R
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.android.ui.components.ui.BottomBar
import com.example.medialion.android.ui.search.ui.MLTitledMediaRow
import com.example.medialion.domain.MediaType

@Composable
fun CollectionScreen(
    modifier: Modifier = Modifier
) {
    ConstraintLayout (
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ){

        val (containerTop, column, bottomBar) = createRefs()

        ConstraintLayout(
            modifier = Modifier.constrainAs(containerTop) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        ) {
            Column (
                modifier = modifier
                    .background(MaterialTheme.colors.background)
                    .padding(horizontal = 16.dp)
                    .constrainAs(column) {
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints

                    }
            ){
                Row (
                    modifier = modifier
                        .background(MaterialTheme.colors.background)
                        .padding(top = 16.dp, bottom = 16.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.back_arrow_icon),
                        contentDescription = "",
                        modifier = modifier
                            .size(30.dp)
                    )

                    Spacer(modifier = modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.about_icon),
                        contentDescription = "",
                        modifier = modifier
                            .size(30.dp)
                    )
                }
                MLTitledMediaRow(
                    rowTitle = "Favorites",
                    media = listOf(
                        MediaItemUI(
                            id = 6102,
                            title = "blandit",
                            isFavorited = true,
                            posterUrl = "https://www.google.com/#q=pertinacia",
                            bannerUrl = "https://duckduckgo.com/?q=his",
                            genreIds = listOf(),
                            overview = "solet",
                            popularity = 4.5,
                            voteAverage = 6.7,
                            voteCount = 8341,
                            releaseYear = "sed",
                            mediaType = MediaType.TV
                        ),
                        MediaItemUI(
                            id = 1234,
                            title = "TWO",
                            isFavorited = true,
                            posterUrl = "https://www.google.com/#q=pertinacia",
                            bannerUrl = "https://duckduckgo.com/?q=his",
                            genreIds = listOf(),
                            overview = "solet",
                            popularity = 4.5,
                            voteAverage = 6.7,
                            voteCount = 8341,
                            releaseYear = "sed",
                            mediaType = MediaType.MOVIE
                        ),
                    ),
                    onMediaItemClicked = {})
            }

            BottomBar(
                modifier = modifier
                    .constrainAs(bottomBar) {
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
            )
        }
        }
}

@Preview
@Composable
private fun CollectionScreenPreview() {
    MediaLionTheme {
        CollectionScreen()
    }

}