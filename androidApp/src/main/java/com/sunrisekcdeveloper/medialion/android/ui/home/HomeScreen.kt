package com.sunrisekcdeveloper.medialion.android.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sunrisekcdeveloper.medialion.TitledMedia
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.android.ui.about.ui.AboutScreen
import com.sunrisekcdeveloper.medialion.android.ui.collections.CollectionScreen
import com.sunrisekcdeveloper.medialion.android.ui.components.ui.BottomBar
import com.sunrisekcdeveloper.medialion.android.ui.components.ui.BottomBarOption
import com.sunrisekcdeveloper.medialion.android.ui.discovery.DiscoveryScreen
import com.sunrisekcdeveloper.medialion.domain.collection.CollectionAction
import com.sunrisekcdeveloper.medialion.domain.collection.CollectionState
import com.sunrisekcdeveloper.medialion.domain.collection.GenreState
import com.sunrisekcdeveloper.medialion.domain.discovery.DiscoveryAction
import com.sunrisekcdeveloper.medialion.domain.discovery.DiscoveryState
import com.sunrisekcdeveloper.medialion.domain.entities.CollectionWithMedia
import com.sunrisekcdeveloper.medialion.domain.search.SearchAction

@Composable
fun HomeScreen(
    discoveryState: DiscoveryState,
    genreState: GenreState,
    collectionState: CollectionState,
    collectionsState: List<CollectionWithMedia>,
    submitSearchAction: (SearchAction) -> Unit,
    submitDiscoveryAction: (DiscoveryAction) -> Unit,
    submitCollectionAction: (CollectionAction) -> Unit,
    onNavigateToSearchScreen: () -> Unit,
) {

    var selectedTab by remember { mutableStateOf(BottomBarOption.DISCOVERY) }
    var showAboutDialog by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = Modifier
            .blur(radius = if (showAboutDialog) 10.dp else 0.dp)
    ) {
        val (coreContent, bottombar) = createRefs()

        Column(
            modifier = Modifier.constrainAs(coreContent) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottombar.top)
            }
        ) {
            when (selectedTab) {
                BottomBarOption.DISCOVERY -> DiscoveryScreen(
                    state = discoveryState,
                    genreState = genreState,
                    submitAction = submitDiscoveryAction,
                    onInfoIconClicked = { showAboutDialog = true },
                    onSearchIconClicked = { onNavigateToSearchScreen() },
                    collectionState = collectionsState,
                    submitSearchAction = submitSearchAction,
                )

                BottomBarOption.COLLECTION -> CollectionScreen(
                    state = collectionState,
                    submitAction = submitCollectionAction,
                    onSearchIconClicked = { onNavigateToSearchScreen() },
                    onInfoIconClicked = { showAboutDialog = true },
                    submitSearchAction = submitSearchAction,
                )
            }
        }

        BottomBar(
            selectedTab = selectedTab,
            onNewSelection = { selectedTab = it },
            modifier = Modifier.constrainAs(bottombar) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )
    }

    if (showAboutDialog) {
        AboutScreen(onDismiss = { showAboutDialog = false })
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreen(
                onNavigateToSearchScreen = {},
                discoveryState = DiscoveryState.Content(listOf(
                    TitledMedia("Content #1", listOf()),
                    TitledMedia("Content #2", listOf()),
                    TitledMedia("Content #3", listOf()),
                )),
                submitDiscoveryAction = {},
                genreState = GenreState.Genres(listOf()),
                collectionsState = emptyList(),
                submitSearchAction = {},
                collectionState = CollectionState.Empty,
                submitCollectionAction = {},

            )
        }
    }
}