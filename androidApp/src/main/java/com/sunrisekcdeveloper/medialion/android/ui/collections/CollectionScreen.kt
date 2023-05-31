@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package com.sunrisekcdeveloper.medialion.android.ui.collections

import android.annotation.SuppressLint
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.sunrisekcdeveloper.medialion.MediaItemUI
import com.sunrisekcdeveloper.medialion.SimpleMediaItem
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.android.ui.components.ui.MLProgress
import com.sunrisekcdeveloper.medialion.android.ui.detailPreview.ui.DetailPreviewScreen
import com.sunrisekcdeveloper.medialion.android.ui.saveToCollection.ui.CollectionItem
import com.sunrisekcdeveloper.medialion.android.ui.saveToCollection.ui.SaveToCollectionScreen
import com.sunrisekcdeveloper.medialion.android.ui.search.ui.MLTitledMediaRow
import com.sunrisekcdeveloper.medialion.domain.MediaType
import com.sunrisekcdeveloper.medialion.domain.collection.CollectionAction
import com.sunrisekcdeveloper.medialion.domain.collection.CollectionState
import com.sunrisekcdeveloper.medialion.domain.entities.CollectionWithMediaUI
import com.sunrisekcdeveloper.medialion.domain.search.SearchAction
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.domain.value.Title
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CollectionScreen(
    state: CollectionState,
    submitAction: (CollectionAction) -> Unit,
    onSearchIconClicked: () -> Unit = {},
    onInfoIconClicked: () -> Unit = {},
    submitSearchAction: (SearchAction) -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {

    var showCollectionDialog by remember { mutableStateOf(false) }

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

    if (showCollectionDialog) {
        SaveToCollectionScreen(
            onDismiss = { showCollectionDialog = false },
            collections = (state as CollectionState.AllCollections).collections
                .map { it.name to it.contents.map { it.id } }
                .map {
                    val selectedMedia = selectedMediaItem
                    val checked = if (selectedMedia != null) {
                        it.second.contains(selectedMedia.id.toInt())
                    } else false
                    CollectionItem(it.first.value, checked)
                },
            onCollectionItemClicked = { collectionName -> },
            onAddToCollection = { collectionName ->
                val selectedMedia = selectedMediaItem
                if (selectedMedia != null) {
                    submitSearchAction(
                        SearchAction.AddToCollection(
                            Title(collectionName),
                            ID(selectedMedia.id.toInt()),
                            selectedMedia.mediaType
                        )
                    )
                }
            },
            onRemoveFromCollection = { collectionName ->
                val selectedMedia = selectedMediaItem
                if (selectedMedia != null) {
                    submitSearchAction(
                        SearchAction.RemoveFromCollection(
                            Title(collectionName),
                            ID(selectedMedia.id.toInt()),
                            selectedMedia.mediaType
                        )
                    )
                }
            },
            onSaveList = {
                submitSearchAction(SearchAction.CreateCollection(Title(it)))
            }
        )
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
                    onMyListClick = {
                        showCollectionDialog = true
                    },
                    modifier = Modifier.blur(radius = if (showCollectionDialog) 10.dp else 0.dp)
                )
            }
        }
    ) {

        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            val (containerTop, content) = createRefs()

            Column(
                modifier = modifier
                    .background(MaterialTheme.colors.background)
                    .padding(horizontal = 16.dp)
                    .padding(top = 100.dp) // hacky fix
                    .constrainAs(containerTop) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(content.top)
                        width = Dimension.fillToConstraints
                    }
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                        .padding(top = 64.dp, bottom = 16.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = "",
                        modifier = modifier
                            .size(30.dp)
                            .clickable {
                                coroutineScope.launch {
                                    modalSheetState.hide()
                                    onSearchIconClicked()
                                }
                            }
                    )

                    Spacer(modifier = modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.about_icon),
                        contentDescription = "",
                        modifier = modifier
                            .size(30.dp)
                            .clickable { onInfoIconClicked() }
                    )
                }
            }

            Column(
                modifier = Modifier.constrainAs(content) {
                    top.linkTo(containerTop.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                    .padding(bottom = 150.dp) // hacky fix
            ) {
                when (state) {
                    is CollectionState.AllCollections -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                        ) {
                            items(state.collections) {
                                MLTitledMediaRow(
                                    rowTitle = it.name.value,
                                    media = it.contents,
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
                    }

                    CollectionState.Empty -> {
                        Text(text = "Empty Collections")
                    }

                    CollectionState.Loading -> {
                        MLProgress()
                    }
                }
            }
        }

    }
}

@Preview
@Composable
private fun CollectionScreenPreview() {
    MediaLionTheme {
        CollectionScreen(
            state = CollectionState.AllCollections(
                collections = listOf(
                    CollectionWithMediaUI(
                        name = Title("Collection Preview"),
                        contents = listOf(
                            MediaItemUI(
                                id = 0,
                                title = "Title",
                                isFavorited = false,
                                posterUrl = "",
                                bannerUrl = "",
                                genreIds = listOf(),
                                overview = "The overview",
                                popularity = 0.0,
                                voteAverage = 0.0,
                                voteCount = 0,
                                releaseYear = "",
                                mediaType = MediaType.MOVIE,
                            )
                        )
                    )
                )
            ),
            submitAction = {},
            onSearchIconClicked = {},
            onInfoIconClicked = {},
            submitSearchAction = {},
        )
    }

}