@file:OptIn(ExperimentalMaterialApi::class)

package com.sunrisekcdeveloper.medialion.android.ui.discovery

import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.constraintlayout.compose.Dimension
import com.sunrisekcdeveloper.medialion.MediaItemUI
import com.sunrisekcdeveloper.medialion.SimpleMediaItem
import com.sunrisekcdeveloper.medialion.TitledMedia
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.android.ui.components.ui.MLProgress
import com.sunrisekcdeveloper.medialion.android.ui.detailPreview.ui.DetailPreviewScreen
import com.sunrisekcdeveloper.medialion.android.ui.discovery.ui.CategoriesDialog
import com.sunrisekcdeveloper.medialion.android.ui.discovery.ui.FilterCategory
import com.sunrisekcdeveloper.medialion.android.ui.discovery.ui.MLFilterCategories
import com.sunrisekcdeveloper.medialion.android.ui.discovery.ui.MLTopBar
import com.sunrisekcdeveloper.medialion.android.ui.saveToCollection.ui.CollectionItem
import com.sunrisekcdeveloper.medialion.android.ui.saveToCollection.ui.SaveToCollectionScreen
import com.sunrisekcdeveloper.medialion.android.ui.search.ui.MLMediaPoster
import com.sunrisekcdeveloper.medialion.android.ui.search.ui.MLTitledMediaRow
import com.sunrisekcdeveloper.medialion.domain.MediaType
import com.sunrisekcdeveloper.medialion.domain.collection.GenreState
import com.sunrisekcdeveloper.medialion.domain.discovery.DiscoveryAction
import com.sunrisekcdeveloper.medialion.domain.discovery.DiscoveryState
import com.sunrisekcdeveloper.medialion.domain.entities.CollectionWithMedia
import com.sunrisekcdeveloper.medialion.domain.search.SearchAction
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.domain.value.Title
import kotlinx.coroutines.launch

@Composable
fun DiscoveryScreen(
    modifier: Modifier = Modifier,
    state: DiscoveryState,
    genreState: GenreState,
    submitAction: (DiscoveryAction) -> Unit,
    onSearchIconClicked: () -> Unit = {},
    onInfoIconClicked: () -> Unit = {},
    showCollectionDialogWithMedia: (SimpleMediaItem) -> Unit,
) {

    var contentFilter by remember { mutableStateOf(FilterCategory.All) }
    var showGenreSelectionDialog by remember { mutableStateOf(false) }

    var selectedMediaItem by remember { mutableStateOf<SimpleMediaItem?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
        animationSpec = spring(
            2.5f
        ),
    )

    if (showGenreSelectionDialog) {
        if (genreState is GenreState.Genres) {
            CategoriesDialog(
                categories = genreState.all,
                onDismiss = { showGenreSelectionDialog = false },
                onSelection = {
                    submitAction(
                        DiscoveryAction.FetchGenreContent(
                            genreId = ID(value = it.id),
                            mediaType = it.mediaType
                        )
                    )
                }
            )
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(12.dp),
        sheetContent = {
            Surface {
                DetailPreviewScreen(
                    mediaItem = selectedMediaItem ?: SimpleMediaItem(
                        "",
                        "",
                        "",
                        "",
                        "",
                        MediaType.MOVIE
                    ),
                    onCloseClick = {
                        selectedMediaItem = null; coroutineScope.launch { modalSheetState.hide() }
                    },
                    onMyListClick = { showCollectionDialogWithMedia(it) },
                )
            }
        }
    ) {
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
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
                        .padding(horizontal = 16.dp, vertical = 25.dp)
                        .padding(bottom = 20.dp)
                        .constrainAs(column) {
                            top.linkTo(parent.top)
                            width = Dimension.fillToConstraints
                        }
                ) {

                    MLTopBar(
                        onSearchIconClicked = {
                            onSearchIconClicked()
                            selectedMediaItem = null
                            coroutineScope.launch { modalSheetState.hide() }
                        },
                        onInfoIconClicked = onInfoIconClicked
                    )

                    MLFilterCategories(
                        selectedFilter = contentFilter,
                        onNewSelection = {
                            if (contentFilter != it || contentFilter == FilterCategory.CATEGORIES) {
                                contentFilter = it
                                when (it) {
                                    FilterCategory.All -> submitAction(
                                        DiscoveryAction.FetchContent(
                                            0
                                        )
                                    )

                                    FilterCategory.MOVIES -> submitAction(
                                        DiscoveryAction.FetchContent(
                                            1
                                        )
                                    )

                                    FilterCategory.SERIES -> submitAction(
                                        DiscoveryAction.FetchContent(
                                            2
                                        )
                                    )

                                    FilterCategory.CATEGORIES -> {
                                        showGenreSelectionDialog = true
                                    }
                                }
                            }
                        }
                    )

                    when (state) {
                        is DiscoveryState.Content -> {
                            if (state.media.size > 1) {
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(20.dp)
                                ) {
                                    items(state.media) {
                                        MLTitledMediaRow(
                                            rowTitle = it.title,
                                            media = it.content,
                                            onMediaItemClicked = {
                                                selectedMediaItem = SimpleMediaItem(
                                                    id = it.id.toString(),
                                                    title = it.title,
                                                    posterUrl = it.posterUrl,
                                                    description = it.overview,
                                                    year = it.releaseYear,
                                                    mediaType = it.mediaType,
                                                )
                                                coroutineScope.launch { modalSheetState.show() }
                                            })
                                    }
                                }
                            } else if (state.media.size == 1) {
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
                                            text = state.media.first().title,
                                            style = MaterialTheme.typography.h3,
                                            color = MaterialTheme.colors.secondary,
                                            modifier = modifier.padding(top = 8.dp, bottom = 6.dp),
                                        )
                                    }
                                    items(state.media.first().content) { singleMovie ->
                                        MLMediaPoster(
                                            mediaItem = SimpleMediaItem(
                                                id = singleMovie.id.toString(),
                                                title = singleMovie.title,
                                                posterUrl = singleMovie.posterUrl,
                                                mediaType = singleMovie.mediaType,
                                            ),
                                            modifier = Modifier.clickable {
                                                selectedMediaItem = SimpleMediaItem(
                                                    id = singleMovie.id.toString(),
                                                    title = singleMovie.title,
                                                    posterUrl = singleMovie.posterUrl,
                                                    description = singleMovie.overview,
                                                    year = singleMovie.releaseYear,
                                                    mediaType = singleMovie.mediaType,
                                                )
                                                coroutineScope.launch { modalSheetState.show() }
                                            }
                                        )
                                    }
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
}


@Preview
@Composable
private fun DiscoveryScreenPreview() {
    MediaLionTheme {
        DiscoveryScreen(
            state = DiscoveryState.Content(
                listOf(
                    TitledMedia("Content #1", listOf()),
                    TitledMedia("Content #2", listOf()),
                    TitledMedia("Content #3", listOf()),
                )
            ),
            submitAction = {},
            onSearchIconClicked = {},
            onInfoIconClicked = {},
            genreState = GenreState.Genres(listOf()),
            showCollectionDialogWithMedia = {},
        )
    }

}