@file:OptIn(ExperimentalMaterialApi::class)

package com.sunrisekcdeveloper.medialion.android.ui.home

import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sunrisekcdeveloper.medialion.SimpleMediaItem
import com.sunrisekcdeveloper.medialion.TitledMedia
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.android.ui.about.ui.AboutScreen
import com.sunrisekcdeveloper.medialion.android.ui.collections.CollectionScreen
import com.sunrisekcdeveloper.medialion.android.ui.components.ui.BottomBar
import com.sunrisekcdeveloper.medialion.android.ui.components.ui.BottomBarOption
import com.sunrisekcdeveloper.medialion.android.ui.detailPreview.ui.DetailPreviewScreen
import com.sunrisekcdeveloper.medialion.android.ui.discovery.DiscoveryScreen
import com.sunrisekcdeveloper.medialion.android.ui.discovery.ui.FilterCategory
import com.sunrisekcdeveloper.medialion.android.ui.saveToCollection.ui.CollectionItem
import com.sunrisekcdeveloper.medialion.android.ui.saveToCollection.ui.SaveToCollectionScreen
import com.sunrisekcdeveloper.medialion.domain.collection.CollectionAction
import com.sunrisekcdeveloper.medialion.domain.collection.CollectionState
import com.sunrisekcdeveloper.medialion.domain.collection.GenreState
import com.sunrisekcdeveloper.medialion.domain.discovery.DiscoveryAction
import com.sunrisekcdeveloper.medialion.domain.discovery.DiscoveryState
import com.sunrisekcdeveloper.medialion.domain.entities.CollectionWithMedia
import com.sunrisekcdeveloper.medialion.domain.search.SearchAction
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.domain.value.Title
import kotlinx.coroutines.launch

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
    selectedTab: BottomBarOption,
    onSelectedTabChange: (BottomBarOption) -> Unit,
) {
    var showAboutDialog by remember { mutableStateOf(false) }
    var collectionDialogMedia by remember { mutableStateOf<SimpleMediaItem?>(null) }
    var detailPreviewDialogMedia by remember { mutableStateOf<SimpleMediaItem?>(null) }
    var contentFilter by remember { mutableStateOf(FilterCategory.All) }

    var currentBottomSheet by remember { mutableStateOf<BottomSheetScreen?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val detailMediaSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
        animationSpec = spring(2.5f),
    )

    LaunchedEffect(detailPreviewDialogMedia) {
        val mediaItem = detailPreviewDialogMedia
        if (mediaItem != null) {
            currentBottomSheet = BottomSheetScreen.DetailPreview(
                mediaItem = mediaItem
            )
            coroutineScope.launch { detailMediaSheetState.show() }
        }
    }

    ModalBottomSheetLayout(
        sheetState = detailMediaSheetState,
        sheetShape = RoundedCornerShape(12.dp),
        sheetContent = {
            when (val sheetContent = currentBottomSheet) {
                is BottomSheetScreen.DetailPreview -> {
                    DetailPreviewScreen(
                        mediaItem = sheetContent.mediaItem,
                        onCloseClick = { coroutineScope.launch { detailMediaSheetState.hide() } },
                        onMyListClick = { collectionDialogMedia = it },
                    )
                }

                null -> {
                    Text(text = "empty") // required view
                    LaunchedEffect(key1 = Unit) {
                        coroutineScope.launch { detailMediaSheetState.hide() }
                    }
                }
            }
        }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .blur(radius = if (showAboutDialog || collectionDialogMedia != null) 10.dp else 0.dp)
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
                        showDetailPreviewDialogWithMedia = { detailPreviewDialogMedia = it },
                        contentFilter = contentFilter,
                        onChangeContentFilter = { contentFilter = it },
                    )

                    BottomBarOption.COLLECTION -> CollectionScreen(
                        state = collectionState,
                        submitAction = submitCollectionAction,
                        onSearchIconClicked = { onNavigateToSearchScreen() },
                        onInfoIconClicked = { showAboutDialog = true },
                        showDetailPreviewDialogWithMedia = { detailPreviewDialogMedia = it }
                    )
                }
            }

            BottomBar(
                selectedTab = selectedTab,
                onNewSelection = { onSelectedTabChange(it) },
                modifier = Modifier.constrainAs(bottombar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            )
        }
    }

    if (showAboutDialog) {
        AboutScreen(onDismiss = { showAboutDialog = false })
    }

    val mediaItem = collectionDialogMedia
    if (mediaItem != null) {
        SaveToCollectionScreen(
            onDismiss = { collectionDialogMedia = null },
            collections = collectionsState
                .map { it.name to it.contents.map { it.id.value } }
                .map {
                    val checked =
                        it.second.contains(mediaItem.id.toInt())
                    CollectionItem(it.first.value, checked)
                },
            onCollectionItemClicked = { collectionName -> },
            onAddToCollection = { collectionName ->
                submitSearchAction(
                    SearchAction.AddToCollection(
                        Title(collectionName),
                        ID(mediaItem.id.toInt()),
                        mediaItem.mediaType
                    )
                )
            },
            onRemoveFromCollection = { collectionName ->
                submitSearchAction(
                    SearchAction.RemoveFromCollection(
                        Title(collectionName),
                        ID(mediaItem.id.toInt()),
                        mediaItem.mediaType
                    )
                )
            },
            onSaveList = {
                submitSearchAction(SearchAction.CreateCollection(Title(it)))
            }
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreen(
                onNavigateToSearchScreen = {},
                discoveryState = DiscoveryState.Content(
                    listOf(
                        TitledMedia("Content #1", listOf()),
                        TitledMedia("Content #2", listOf()),
                        TitledMedia("Content #3", listOf()),
                    )
                ),
                submitDiscoveryAction = {},
                genreState = GenreState.Genres(listOf()),
                collectionsState = emptyList(),
                submitSearchAction = {},
                collectionState = CollectionState.Empty,
                submitCollectionAction = {},
                selectedTab = BottomBarOption.DISCOVERY,
                onSelectedTabChange = {}
            )
        }
    }
}

sealed class BottomSheetScreen {
    data class DetailPreview(val mediaItem: SimpleMediaItem) : BottomSheetScreen()
}