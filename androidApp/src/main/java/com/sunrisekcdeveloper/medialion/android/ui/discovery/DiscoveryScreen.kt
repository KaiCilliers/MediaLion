package com.sunrisekcdeveloper.medialion.android.ui.discovery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.sunrisekcdeveloper.medialion.TitledMedia
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.android.ui.components.ui.MLProgress
import com.sunrisekcdeveloper.medialion.android.ui.discovery.ui.FilterCategories
import com.sunrisekcdeveloper.medialion.android.ui.search.ui.MLTitledMediaRow
import com.sunrisekcdeveloper.medialion.domain.discovery.DiscoveryAction
import com.sunrisekcdeveloper.medialion.domain.discovery.DiscoveryState

@Composable
fun DiscoveryScreen(
    modifier: Modifier = Modifier,
    state: DiscoveryState,
    submitAction: (DiscoveryAction) -> Unit,
    onSearchIconClicked: () -> Unit = {},
    onInfoIconClicked: () -> Unit = {},
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
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
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(horizontal = 16.dp)
                    .constrainAs(column) {
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                    }

            ) {
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .padding(top = 16.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { onSearchIconClicked() }

                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.logo_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .size(60.dp)

                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.about_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { onInfoIconClicked() }

                    )
                }

                FilterCategories()


                when(state) {
                    is DiscoveryState.Content -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            items(state.media) {
                                MLTitledMediaRow(
                                    rowTitle = it.title,
                                    media = it.content,
                                    onMediaItemClicked = {})
                            }
                        }
                    }
                    is DiscoveryState.Error -> Text("Something went wrong ${state.msg} - ${state.exception}")
                    DiscoveryState.Loading -> MLProgress()
                }
            }
        }

    }


}


@Preview
@Composable
private fun DiscoveryScreenPreview() {
    MediaLionTheme {
        DiscoveryScreen(
            state = DiscoveryState.Content(listOf(
                TitledMedia("Content #1", listOf()),
                TitledMedia("Content #2", listOf()),
                TitledMedia("Content #3", listOf()),
            )),
            submitAction = {},
            onSearchIconClicked = {},
            onInfoIconClicked = {}
        )
    }

}