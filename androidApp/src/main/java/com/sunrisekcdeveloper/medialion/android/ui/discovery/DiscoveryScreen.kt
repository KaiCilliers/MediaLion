package com.sunrisekcdeveloper.medialion.android.ui.discovery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.sunrisekcdeveloper.medialion.MediaItemUI
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.android.ui.components.ui.BottomBar
import com.sunrisekcdeveloper.medialion.android.ui.discovery.ui.FilterCategories
import com.sunrisekcdeveloper.medialion.android.ui.search.ui.MLTitledMediaRow
import com.sunrisekcdeveloper.medialion.domain.MediaType

@Composable
fun DiscoveryScreen(
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        val (containerTop, column) = createRefs()

        ConstraintLayout(
            modifier = Modifier.constrainAs(containerTop) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        ) {
            Column(
                modifier = modifier
                    .background(MaterialTheme.colors.background)
                    .padding(horizontal = 16.dp)
                    .constrainAs(column) {
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                    }

            ) {
                Row(
                    modifier = modifier
                        .background(MaterialTheme.colors.background)
                        .padding(top = 16.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = "",
                        modifier = modifier
                            .size(30.dp)

                    )
                    Spacer(modifier = modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.logo_icon),
                        contentDescription = "",
                        modifier = modifier
                            .size(60.dp)

                    )
                    Spacer(modifier = modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.about_icon),
                        contentDescription = "",
                        modifier = modifier
                            .size(30.dp)

                    )
                }
                FilterCategories()


                val media = (0..10).map {
                    MediaItemUI(
                        id = 9528 + it,
                        title = "Title $it",
                        isFavorited = false,
                        posterUrl = "https://duckduckgo.com/?q=aperiri",
                        bannerUrl = "http://www.bing.com/search?q=homero",
                        genreIds = listOf(),
                        overview = "sadipscing",
                        popularity = 32.33,
                        voteAverage = 34.35,
                        voteCount = 7426,
                        releaseYear = "auctor",
                        mediaType = MediaType.MOVIE,
                    )
                }

                val allMedia =
                    listOf(media, media, media, media, media, media, media, media, media, media)
                LazyColumn(
                    modifier = modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(allMedia) {
                        MLTitledMediaRow(
                            rowTitle = "Horror",
                            media = media,
                            onMediaItemClicked = {})
                    }
                }


            }
        }

    }


}


@Preview
@Composable
private fun DiscoveryScreenPreview() {
    MediaLionTheme {
        DiscoveryScreen()
    }

}