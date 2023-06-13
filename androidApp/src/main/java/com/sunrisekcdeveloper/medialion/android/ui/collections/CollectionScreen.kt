@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package com.sunrisekcdeveloper.medialion.android.ui.collections

import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.sunrisekcdeveloper.medialion.MediaItemUI
import com.sunrisekcdeveloper.medialion.SimpleMediaItem
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.android.ui.components.ui.MLProgress
import com.sunrisekcdeveloper.medialion.android.ui.search.ui.MLMediaPoster
import com.sunrisekcdeveloper.medialion.android.ui.search.ui.MLTitledMediaRow
import com.sunrisekcdeveloper.medialion.domain.MediaType
import com.sunrisekcdeveloper.medialion.domain.collection.CollectionAction
import com.sunrisekcdeveloper.medialion.domain.collection.CollectionState
import com.sunrisekcdeveloper.medialion.domain.entities.CollectionWithMediaUI
import com.sunrisekcdeveloper.medialion.domain.search.usecases.SetupInitialCollectionUseCase
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.domain.value.Title
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CollectionScreen(
    state: CollectionState,
    submitAction: (CollectionAction) -> Unit,
    onSearchIconClicked: () -> Unit,
    onInfoIconClicked: () -> Unit,
    showDetailPreviewDialogWithMedia: (SimpleMediaItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentBottomSheet by remember { mutableStateOf<BottomSheetScreen?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
        animationSpec = spring(
            2.5f
        ),
    )
    var editMode by remember {
        mutableStateOf(false)
    }
    var collectionMedia by remember {
        mutableStateOf<List<MediaItemUI>?>(null)
    }
    var editTitleMode by remember {
        mutableStateOf(false)
    }
    var headingValue by remember {
        mutableStateOf("")
    }

    // State change callback
    LaunchedEffect(modalSheetState) {
        snapshotFlow { modalSheetState.isVisible }.collect { isVisible ->
            println("deadpool $isVisible")
            if (!isVisible) {
                collectionMedia = emptyList()
                headingValue = ""
                editMode = false
                currentBottomSheet = null
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(12.dp),
        sheetContent = {
            when (val sheet = currentBottomSheet) {
                is BottomSheetScreen.EntireCollection -> {
                    if (collectionMedia == null || headingValue.isEmpty()) {
                        collectionMedia = sheet.media
                        headingValue = sheet.title.value
                    }
                    Box {
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
                                if (editTitleMode) {
                                    TextField(
                                        value = headingValue,
                                        onValueChange = { newText ->
                                            headingValue = newText
                                        },
                                        modifier = Modifier
                                            .padding(top = 8.dp, bottom = 6.dp)
                                            .clickable {
                                                editTitleMode = !editTitleMode
                                            },
                                        textStyle = MaterialTheme.typography.h3,
                                    )
                                } else {
                                    if (sheet.title.value != headingValue) {
                                        submitAction(
                                            CollectionAction.RenameCollection(
                                                oldCollectionName = sheet.title,
                                                newCollectionName = Title(value = headingValue)
                                            )
                                        )
                                    }
                                    Text(
                                        text = headingValue.trim(),
                                        style = MaterialTheme.typography.subtitle1,
                                        color = MaterialTheme.colors.secondary,
                                        modifier = modifier
                                            .padding(top = 8.dp, bottom = 6.dp)
                                            .clickable {
                                                if (!SetupInitialCollectionUseCase.PRE_PACKAGED_COLLECTIONS.contains(headingValue.trim())) {
                                                    editTitleMode = !editTitleMode
                                                }
                                            },
                                    )
                                }
                            }

                            if (editMode && !SetupInitialCollectionUseCase.PRE_PACKAGED_COLLECTIONS.contains(headingValue.trim())) {
                                item(span = { GridItemSpan(3) }) {
                                    Text(
                                        text = "Delete Collection",
                                        style = MaterialTheme.typography.caption,
                                        color = Color.Red,
                                        textAlign = TextAlign.End,
                                        textDecoration = TextDecoration.Underline,
                                        modifier = modifier
                                            .padding(top = 8.dp, bottom = 6.dp)
                                            .clickable {
                                                submitAction(CollectionAction.DeleteCollection(Title(headingValue)))
                                                coroutineScope.launch { modalSheetState.hide() }
                                            },
                                    )
                                }
                            }

                            items(collectionMedia ?: emptyList()) { singleMovie ->
                                val simple = SimpleMediaItem(
                                    id = singleMovie.id.toString(),
                                    title = singleMovie.title,
                                    posterUrl = singleMovie.posterUrl,
                                    mediaType = singleMovie.mediaType,
                                    description = singleMovie.overview,
                                    year = singleMovie.releaseYear,
                                )
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    MLMediaPoster(
                                        mediaItem = simple,
                                        modifier = Modifier.clickable {
                                            showDetailPreviewDialogWithMedia(
                                                simple
                                            )
                                        }
                                    )
                                    if (editMode) {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_boxed_x),
                                            contentDescription = "",
                                            colorFilter = ColorFilter.tint(Color.Red),
                                            modifier = modifier
                                                .padding(bottom = 80.dp, end = 20.dp)
                                                .size(90.dp)
                                                .clickable {
                                                    submitAction(
                                                        CollectionAction.RemoveFromCollection(
                                                            collectionName = sheet.title,
                                                            mediaId = ID(singleMovie.id),
                                                            mediaType = singleMovie.mediaType,
                                                        )
                                                    )
                                                    val items = collectionMedia?.toMutableList() ?: mutableListOf()
                                                    items.removeIf { it.id == singleMovie.id }
                                                    collectionMedia = items
                                                }
                                                .align(Alignment.Center),

                                            )
                                    }
                                }
                            }

                        }
                        Image(
                            painter = painterResource(
                                id =
                                if (editMode) {
                                    R.drawable.name_created_icon
                                } else {
                                    R.drawable.edit_icon
                                }
                            ),
                            contentDescription = "",
                            modifier = modifier
                                .padding(bottom = 80.dp, end = 20.dp)
                                .size(90.dp)
                                .clickable {
                                    editMode = !editMode
                                }
                                .align(Alignment.BottomEnd),

                            )


                    }
                }

                null -> {
                    Text(text = "empty") // required view
                    LaunchedEffect(key1 = Unit) {
                        coroutineScope.launch { modalSheetState.hide() }
                    }
                }
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
                modifier = Modifier
                    .constrainAs(content) {
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
                                if (it.contents.isNotEmpty()) {
                                    MLTitledMediaRow(
                                        rowTitle = it.name.value,
                                        media = it.contents,
                                        onMediaItemClicked = {
                                            showDetailPreviewDialogWithMedia(
                                                SimpleMediaItem(
                                                    id = it.id.toString(),
                                                    title = it.title,
                                                    posterUrl = it.posterUrl,
                                                    description = it.overview,
                                                    year = it.releaseYear,
                                                    mediaType = it.mediaType,
                                                )
                                            )
                                        },
                                        onTitleClicked = {
                                            currentBottomSheet = BottomSheetScreen.EntireCollection(
                                                title = it.name,
                                                media = it.contents
                                            )
                                            coroutineScope.launch { modalSheetState.show() }
                                        }
                                    )
                                }
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
            showDetailPreviewDialogWithMedia = {}
        )
    }

}

sealed class BottomSheetScreen {
    data class EntireCollection(val title: Title, val media: List<MediaItemUI>) :
        BottomSheetScreen()
}