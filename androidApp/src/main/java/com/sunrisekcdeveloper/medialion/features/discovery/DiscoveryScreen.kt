package com.sunrisekcdeveloper.medialion.features.discovery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.DiscoveryPage
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.MediaWithTitle
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.TitledMediaList
import com.sunrisekcdeveloper.medialion.features.collections.PreviewWrapper
import com.sunrisekcdeveloper.medialion.features.discovery.components.FilterCategory
import com.sunrisekcdeveloper.medialion.features.discovery.components.MLFilterCategories
import com.sunrisekcdeveloper.medialion.features.discovery.components.MLTopBar
import com.sunrisekcdeveloper.medialion.features.home.GlobalRouter
import com.sunrisekcdeveloper.medialion.features.search.SearchKey
import com.sunrisekcdeveloper.medialion.features.search.components.MLMediaPoster
import com.sunrisekcdeveloper.medialion.features.search.components.MLTitledMediaRow
import com.sunrisekcdeveloper.medialion.features.shared.MLProgress
import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI
import com.sunrisekcdeveloper.medialion.utils.debug
import com.sunrisekcdeveloper.medialion.utils.rememberService
import com.zhuinden.simplestackcomposeintegration.core.LocalBackstack

@Composable
fun DiscoveryScreen() {
    val backstack = LocalBackstack.current
    val globalRouter = rememberService<GlobalRouter>()
    val discoveryViewModel = rememberService<DiscoveryViewModel>()

    val discoveryState by discoveryViewModel.discoveryState.collectAsState()

    DiscoveryScreenContent(
        discoveryState = discoveryState,
        navigateToSearchScreen = { backstack.parentServices?.goTo(SearchKey(globalRouter)) },
        showInfoDialog = { globalRouter.infoRouter.show() },
        fetchContentForPage = { page -> discoveryViewModel.submit(FetchPageMediaContent(page)) },
        showCategoryDialog = {
            globalRouter
                .categoryDialogRouter
                .showWithResult {category -> discoveryViewModel.submit(FetchMediaForCategory(category)) }
        },
        showMediaPreviewSheet = { domainItem -> globalRouter.mediaPreviewRouter.show(MediaItemUI.from(domainItem)) }
    )

}

@Composable
fun DiscoveryScreenContent(
    discoveryState: DiscoveryUIState,
    fetchContentForPage: (DiscoveryPage) -> Unit,
    showCategoryDialog: () -> Unit,
    navigateToSearchScreen: () -> Unit,
    showInfoDialog: () -> Unit,
    showMediaPreviewSheet: (SingleMediaItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        Column(
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            MLTopBar(
                onSearchIconClicked = { navigateToSearchScreen() },
                onInfoIconClicked = { showInfoDialog() },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            MLFilterCategories(
                selectedFilter = when (discoveryState.tabSelection) {
                    DiscoveryScreenTabSelection.All -> FilterCategory.All
                    DiscoveryScreenTabSelection.Categories -> FilterCategory.CATEGORIES
                    DiscoveryScreenTabSelection.Movies -> FilterCategory.MOVIES
                    DiscoveryScreenTabSelection.TVShows -> FilterCategory.SERIES
                },
                onNewSelection = { selection ->
                    when (selection) {
                        FilterCategory.All -> fetchContentForPage(DiscoveryPage.All)
                        FilterCategory.MOVIES -> fetchContentForPage(DiscoveryPage.Movies)
                        FilterCategory.SERIES -> fetchContentForPage(DiscoveryPage.TVShows)
                        FilterCategory.CATEGORIES -> showCategoryDialog()
                    }
                },
            )

            when (discoveryState) {
                is DiscoveryUIState.Content -> {
                    if (discoveryState.media.all().size > 1) {
                        // show horizontal lists
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            items(discoveryState.media.all()) {titledMedia ->
                                MLTitledMediaRow(
                                    rowTitle = titledMedia.title().toString(),
                                    media = titledMedia.media().map { domain -> MediaItemUI.from(domain) },
                                    onMediaItemClicked = { mediaItem -> showMediaPreviewSheet(mediaItem.toDomain()) }
                                )
                            }
                        }
                    } else {
                        // show media in single grid format
                        val mediaWithTitle = discoveryState.media.all().first()
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            modifier = modifier
                                .background(MaterialTheme.colors.background)
                                .fillMaxSize(),
                            contentPadding = PaddingValues(22.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            horizontalArrangement = Arrangement.spacedBy(24.dp)

                        ) {
                            item(span = { GridItemSpan(3) }) {
                                Text(
                                    text = mediaWithTitle.title().toString(),
                                    style = MaterialTheme.typography.h3,
                                    color = MaterialTheme.colors.secondary,
                                    modifier = modifier.padding(top = 8.dp, bottom = 6.dp),
                                )
                            }
                            items(mediaWithTitle.media()) { domainMedia ->
                                val mediaItem = MediaItemUI.from(domainMedia)
                                MLMediaPoster(
                                    posterUrl = mediaItem.posterUrl,
                                    title = domainMedia.name(),
                                    modifier = Modifier.clickable { showMediaPreviewSheet(domainMedia) }
                                )
                            }
                        }
                    }
                }

                is DiscoveryUIState.Error -> debug { "$discoveryState" }
                is DiscoveryUIState.Loading -> MLProgress()
            }

        }
    }
}

@Preview
@Composable
private fun DiscoveryScreenPreview() {
    PreviewWrapper {
        val state: DiscoveryUIState = DiscoveryUIState.Content(
            media = TitledMediaList.Def(MediaWithTitle.Def("adasd")),
            tabSelection = DiscoveryScreenTabSelection.Categories
        )
        DiscoveryScreenContent(
            discoveryState = state,
            navigateToSearchScreen = {},
            showInfoDialog = {},
            fetchContentForPage = {},
            showCategoryDialog = {},
            showMediaPreviewSheet = {},
        )
    }

}