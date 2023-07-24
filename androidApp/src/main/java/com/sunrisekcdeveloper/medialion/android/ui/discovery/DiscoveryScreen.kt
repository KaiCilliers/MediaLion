//@file:OptIn(ExperimentalMaterialApi::class)
//
//package com.sunrisekcdeveloper.medialion.android.ui.discovery
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.GridItemSpan
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.material.ExperimentalMaterialApi
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.constraintlayout.compose.ConstraintLayout
//import androidx.constraintlayout.compose.Dimension
//import com.sunrisekcdeveloper.medialion.oldArch.SimpleMediaItem
//import com.sunrisekcdeveloper.medialion.oldArch.TitledMedia
//import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
//import com.sunrisekcdeveloper.medialion.android.ui.components.ui.MLProgress
//import com.sunrisekcdeveloper.medialion.android.ui.discovery.ui.CategoriesDialog
//import com.sunrisekcdeveloper.medialion.android.ui.discovery.ui.FilterCategory
//import com.sunrisekcdeveloper.medialion.android.ui.discovery.ui.MLFilterCategories
//import com.sunrisekcdeveloper.medialion.android.ui.discovery.ui.MLTopBar
//import com.sunrisekcdeveloper.medialion.android.ui.search.ui.MLMediaPoster
//import com.sunrisekcdeveloper.medialion.android.ui.search.ui.MLTitledMediaRow
//import com.sunrisekcdeveloper.medialion.features.discovery.CategoriesAction
//import com.sunrisekcdeveloper.medialion.features.discovery.CategoriesUIState
//import com.sunrisekcdeveloper.medialion.features.discovery.DiscoveryNewActions
//import com.sunrisekcdeveloper.medialion.features.discovery.DiscoveryUIState
//import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType
//import com.sunrisekcdeveloper.medialion.oldArch.domain.collection.GenreState
//import com.sunrisekcdeveloper.medialion.oldArch.domain.value.ID
//
//@Composable
//fun DiscoveryScreen(
//    discoveryUIState: DiscoveryUIState,
//    categoriesUIState: CategoriesUIState,
//    contentFilter: FilterCategory,
//    onChangeContentFilter: (FilterCategory) -> Unit,
//    submitAction: (DiscoveryNewActions) -> Unit,
//    onSearchIconClicked: () -> Unit,
//    onInfoIconClicked: () -> Unit,
//    showDetailPreviewDialogWithMedia: (SimpleMediaItem) -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    var showGenreSelectionDialog by remember { mutableStateOf(false) }
//
//    var selectedMediaItem by remember { mutableStateOf<SimpleMediaItem?>(null) }
//
//    if (showGenreSelectionDialog) {
//        if (categoriesUIState is GenreState.Genres) {
//            CategoriesDialog(
//                categories = categoriesUIState.all,
//                onDismiss = { showGenreSelectionDialog = false },
//                onSelection = {
//                    submitAction(
//                        DiscoveryAction.FetchGenreContent(
//                            genreId = ID(value = it.id),
//                            mediaType = it.mediaType
//                        )
//                    )
//                }
//            )
//        }
//    }
//    ConstraintLayout(
//        modifier = modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//    ) {
//
//        val (containerTop, column) = createRefs()
//
//        ConstraintLayout(
//            modifier = Modifier.constrainAs(containerTop) {
//                top.linkTo(parent.top)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//                width = Dimension.fillToConstraints
//            }
//        ) {
//            Column(
//                modifier = Modifier
//                    .background(MaterialTheme.colors.background)
//                    .padding(horizontal = 16.dp, vertical = 25.dp)
//                    .padding(bottom = 20.dp)
//                    .constrainAs(column) {
//                        top.linkTo(parent.top)
//                        width = Dimension.fillToConstraints
//                    }
//            ) {
//
//                MLTopBar(
//                    onSearchIconClicked = {
//                        onSearchIconClicked()
//                        selectedMediaItem = null
//                    },
//                    onInfoIconClicked = onInfoIconClicked
//                )
//
//                MLFilterCategories(
//                    selectedFilter = contentFilter,
//                    onNewSelection = {
//                        if (contentFilter != it || contentFilter == FilterCategory.CATEGORIES) {
//                            onChangeContentFilter(it)
//                            when (it) {
//                                FilterCategory.All -> submitAction(
//                                    DiscoveryAction.FetchContent(null)
//                                )
//
//                                FilterCategory.MOVIES -> submitAction(
//                                    DiscoveryAction.FetchContent(MediaType.MOVIE)
//                                )
//
//                                FilterCategory.SERIES -> submitAction(
//                                    DiscoveryAction.FetchContent(MediaType.TV)
//                                )
//
//                                FilterCategory.CATEGORIES -> {
//                                    showGenreSelectionDialog = true
//                                }
//                            }
//                        }
//                    }
//                )
//
//                when (discoveryUIState) {
//                    is DiscoveryState.Content -> {
//                        if (discoveryUIState.media.size > 1) {
//                            LazyColumn(
//                                verticalArrangement = Arrangement.spacedBy(20.dp)
//                            ) {
//                                items(discoveryUIState.media) {
//                                    MLTitledMediaRow(
//                                        rowTitle = it.title,
//                                        media = it.content,
//                                        onMediaItemClicked = {
//                                            showDetailPreviewDialogWithMedia(
//                                                SimpleMediaItem(
//                                                    id = it.id.toString(),
//                                                    title = it.title,
//                                                    posterUrl = it.posterUrl,
//                                                    description = it.overview,
//                                                    year = it.releaseYear,
//                                                    mediaType = it.mediaType,
//                                                )
//                                            )
//                                        })
//                                }
//                            }
//                        } else if (discoveryUIState.media.size == 1) {
//                            LazyVerticalGrid(
//                                columns = GridCells.Fixed(3),
//                                modifier = modifier
//                                    .background(MaterialTheme.colors.background)
//                                    .fillMaxSize(),
//                                contentPadding = PaddingValues(22.dp),
//                                verticalArrangement = Arrangement.spacedBy(24.dp),
//                                horizontalArrangement = Arrangement.spacedBy(24.dp)
//
//                            ) {
//                                item(span = { GridItemSpan(3) }) {
//                                    Text(
//                                        text = discoveryUIState.media.first().title,
//                                        style = MaterialTheme.typography.h3,
//                                        color = MaterialTheme.colors.secondary,
//                                        modifier = modifier.padding(top = 8.dp, bottom = 6.dp),
//                                    )
//                                }
//                                items(discoveryUIState.media.first().content) { singleMovie ->
//                                    MLMediaPoster(
//                                        mediaItem = SimpleMediaItem(
//                                            id = singleMovie.id.toString(),
//                                            title = singleMovie.title,
//                                            posterUrl = singleMovie.posterUrl,
//                                            mediaType = singleMovie.mediaType,
//                                        ),
//                                        modifier = Modifier.clickable {
//                                            showDetailPreviewDialogWithMedia(
//                                                SimpleMediaItem(
//                                                    id = singleMovie.id.toString(),
//                                                    title = singleMovie.title,
//                                                    posterUrl = singleMovie.posterUrl,
//                                                    description = singleMovie.overview,
//                                                    year = singleMovie.releaseYear,
//                                                    mediaType = singleMovie.mediaType,
//                                                )
//                                            )
//                                        }
//                                    )
//                                }
//                            }
//                        }
//                    }
//
//                    is DiscoveryState.Error -> Text("Something went wrong ${discoveryUIState.msg} - ${discoveryUIState.exception}")
//
//                    DiscoveryState.Loading -> MLProgress()
//                }
//            }
//        }
//    }
//}
//
//
//@Preview
//@Composable
//private fun DiscoveryScreenPreview() {
//    MediaLionTheme {
//        DiscoveryScreen(
//            discoveryUIState = DiscoveryState.Content(
//                listOf(
//                    TitledMedia("Content #1", listOf()),
//                    TitledMedia("Content #2", listOf()),
//                    TitledMedia("Content #3", listOf()),
//                )
//            ),
//            submitAction = {},
//            onSearchIconClicked = {},
//            onInfoIconClicked = {},
//            categoriesUIState = GenreState.Genres(listOf()),
//            showDetailPreviewDialogWithMedia = {},
//            contentFilter = FilterCategory.All,
//            onChangeContentFilter = {},
//        )
//    }
//
//}