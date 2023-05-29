package com.sunrisekcdeveloper.medialion.android.ui.discovery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.sunrisekcdeveloper.medialion.TitledMedia
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.android.ui.components.ui.MLProgress
import com.sunrisekcdeveloper.medialion.android.ui.discovery.ui.FilterCategory
import com.sunrisekcdeveloper.medialion.android.ui.discovery.ui.MLFilterCategories
import com.sunrisekcdeveloper.medialion.android.ui.discovery.ui.MLTopBar
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

    var contentFilter by remember { mutableStateOf(FilterCategory.All) }

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

                MLTopBar(
                    onSearchIconClicked = onSearchIconClicked,
                    onInfoIconClicked = onInfoIconClicked
                )

                MLFilterCategories(
                    selectedFilter = contentFilter,
                    onNewSelection = {
                        if (contentFilter != it) {
                            contentFilter = it
                            when (it) {
                                FilterCategory.All -> submitAction(DiscoveryAction.FetchContent(0))
                                FilterCategory.MOVIES -> submitAction(DiscoveryAction.FetchContent(1))
                                FilterCategory.SERIES -> submitAction(DiscoveryAction.FetchContent(2))
                                FilterCategory.CATEGORIES -> {}
                            }
                        }
                    }
                )

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