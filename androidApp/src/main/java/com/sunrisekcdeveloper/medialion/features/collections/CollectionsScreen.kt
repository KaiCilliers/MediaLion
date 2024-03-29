package com.sunrisekcdeveloper.medialion.features.collections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.features.discovery.components.MLTopBar
import com.sunrisekcdeveloper.medialion.features.home.GlobalRouter
import com.sunrisekcdeveloper.medialion.features.mycollection.FailedToFetchCollections
import com.sunrisekcdeveloper.medialion.features.mycollection.Loading
import com.sunrisekcdeveloper.medialion.features.mycollection.MyCollectionsContent
import com.sunrisekcdeveloper.medialion.features.mycollection.MyCollectionsUIState
import com.sunrisekcdeveloper.medialion.features.search.SearchKey
import com.sunrisekcdeveloper.medialion.features.search.components.MLTitledMediaRow
import com.sunrisekcdeveloper.medialion.features.shared.MLProgress
import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI
import com.sunrisekcdeveloper.medialion.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.utils.rememberService
import com.zhuinden.simplestackcomposeintegration.core.LocalBackstack

@Composable
fun CollectionsScreen() {
    val backstack = LocalBackstack.current
    val globalRouter = rememberService<GlobalRouter>()
    val collectionsViewModel = rememberService<CollectionViewModel>()

    val collectionsState by collectionsViewModel.collectionsState.collectAsState()

    CollectionScreenContent(
        collectionState = collectionsState,
        navigateToSearchScreen = { backstack.parentServices?.goTo(SearchKey(globalRouter)) },
        openInfoDialog = { globalRouter.infoRouter.show() },
        openCollectionDetailSheet = { collection -> globalRouter.fullCollectionRouter.show(collection) },
        openMediaPreviewSheet = { item -> globalRouter.mediaPreviewRouter.show(item) }
    )
}

@Composable
private fun CollectionScreenContent(
    collectionState: MyCollectionsUIState,
    navigateToSearchScreen: () -> Unit,
    openInfoDialog: () -> Unit,
    openMediaPreviewSheet: (MediaItemUI) -> Unit,
    openCollectionDetailSheet: (CollectionNew) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        MLTopBar(
            onSearchIconClicked = { navigateToSearchScreen() },
            onInfoIconClicked = { openInfoDialog() },
            showLogo = false,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colors.background)
        ) {
            when (collectionState) {
                FailedToFetchCollections -> TODO()
                Loading -> MLProgress()
                is MyCollectionsContent -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .padding(bottom = 20.dp)
                    ) {
                        items(collectionState.collections) { collection ->
                            MLTitledMediaRow(
                                rowTitle = collection.title().toString(),
                                media = collection.media().map { domainModel -> MediaItemUI.from(domainModel) },
                                onMediaItemClicked = { item -> openMediaPreviewSheet(item) },
                                onTitleClicked = { openCollectionDetailSheet(collection) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CollectionScreenPreview() {
    PreviewWrapper {
        CollectionScreenContent(
            navigateToSearchScreen = {},
            openInfoDialog = {},
            openMediaPreviewSheet = {},
            openCollectionDetailSheet = {},
            collectionState = MyCollectionsContent(
                id = ID.Def(),
                collections = listOf(
                    CollectionNew.Def("Title One", media = listOf(
                        SingleMediaItem.Movie("#1 Movie"),
                        SingleMediaItem.Movie("#2 Movie"),
                        SingleMediaItem.Movie("#3 Movie"),
                        SingleMediaItem.Movie("#4 Movie"),
                        SingleMediaItem.Movie("#5 Movie"),
                        SingleMediaItem.Movie("#6 Movie"),
                    )),
                    CollectionNew.Def("Title Two", media = listOf(
                        SingleMediaItem.TVShow("#1 TV"),
                        SingleMediaItem.TVShow("#2 TV"),
                        SingleMediaItem.TVShow("#3 TV"),
                        SingleMediaItem.TVShow("#4 TV"),
                        SingleMediaItem.TVShow("#5 TV"),
                        SingleMediaItem.TVShow("#6 TV"),
                    )),
                ),
            ),
        )
    }
}

@Composable
fun PreviewWrapper(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    MediaLionTheme {
        Surface(modifier = modifier.fillMaxSize()) {
            content()
        }
    }
}