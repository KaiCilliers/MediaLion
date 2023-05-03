package com.example.medialion.android.ui.search.ui

import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medialion.ColorRes
import com.example.medialion.StringRes
import com.example.medialion.android.R
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.android.ui.about.ui.AboutScreen
import com.example.medialion.android.ui.detailPreview.ui.DetailPreviewScreen
import com.example.medialion.android.ui.saveToCollection.ui.CollectionItem
import com.example.medialion.android.ui.saveToCollection.ui.SaveToCollectionScreen
import com.example.medialion.domain.components.search.SearchAction
import com.example.medialion.domain.components.search.SearchState
import com.example.medialion.domain.models.MediaItemUI
import com.example.medialion.domain.models.MediaType
import com.example.medialion.domain.models.SimpleMediaItem
import com.zhuinden.simplestack.Backstack
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    state: SearchState,
    collectionState: List<Pair<String, List<Int>>>,
    submitAction: (SearchAction) -> Unit,
    backstack: Backstack,
) {
    val context = LocalContext.current
    var showAboutDialog by remember { mutableStateOf(false) }
    var showCollectionDialog by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

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

    if (showAboutDialog) {
        AboutScreen { showAboutDialog = false }
    }

    if (showCollectionDialog) {
        SaveToCollectionScreen(
            onDismiss = { showCollectionDialog = false },
            collections = collectionState
                .map {
                    val selectedMedia = selectedMediaItem
                    val checked = if (selectedMedia != null) {
                        it.second.contains(selectedMedia.id.toInt())
                    } else false
                    CollectionItem(it.first, checked)
                },
            onCollectionItemClicked = { collectionName -> },
            onAddToCollection = { collectionName ->
                val selectedMedia = selectedMediaItem
                if (selectedMedia != null) {
                    submitAction(SearchAction.AddToCollection(collectionName, selectedMedia.id.toInt()))
                }
            },
            onRemoveFromCollection = { collectionName ->
                val selectedMedia = selectedMediaItem
                if (selectedMedia != null) {
                    submitAction(SearchAction.RemoveFromCollection(collectionName, selectedMedia.id.toInt()))
                }
            },
            onSaveList = {
                submitAction(SearchAction.CreateCollection(it))
            }
        )
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(12.dp),
        sheetContent = {
            Surface {
                DetailPreviewScreen(
                    mediaItem = selectedMediaItem ?: SimpleMediaItem("", "", "", "", "", MediaType.MOVIE),
                    onCloseClick = {
                        selectedMediaItem = null; coroutineScope.launch { modalSheetState.hide() }
                    },
                    onMyListClick = { showCollectionDialog = true },
                    modifier = Modifier.blur(radius = if (showCollectionDialog) 10.dp else 0.dp)
                )
            }
        }) {

        Column(
            Modifier
                .background(MaterialTheme.colors.background)
                .blur(radius = if (showAboutDialog || showCollectionDialog) 10.dp else 0.dp)
                // todo fix tapping away keyboard also interacts with UI successfully
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN,
                        MotionEvent.ACTION_MOVE,
                        MotionEvent.ACTION_UP -> {
                            keyboardController?.hide()
                            false
                        }

                        else -> {
                            false
                        }
                    }
                }
        ) {
            Row(
                modifier = Modifier
                    .padding(15.dp)
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_arrow_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            Toast
                                .makeText(
                                    context,
                                    "Navigate back functionality...",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        },
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.about_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable { showAboutDialog = true }
                )
            }

            Text("Search Movie")
            MLSearchBar(
                searchQuery = state.searchQuery,
                labelText = stringResource(id = StringRes.emptySearch.resourceId),
                onSearchQueryTextChange = {
                    submitAction(SearchAction.SubmitSearchQuery(it))
                },
                onClearSearchText = { submitAction(SearchAction.ClearSearchText) },
            )
            when (state) {
                is SearchState.Empty -> {
                    SearchEmptyState()
                }

                is SearchState.Idle -> {
                    SearchIdleState(
                        rowTitle = stringResource(id = com.example.medialion.R.string.top_suggestions),
                        media = state.suggestedMedia,
                        onMediaClicked = {
                            submitAction(SearchAction.GetMovieDetails(it.id))
                            selectedMediaItem = SimpleMediaItem(
                                id = it.id.toString(),
                                title = it.title,
                                posterUrl = it.posterUrl,
                                description = it.overview,
                                year = it.releaseYear,
                                mediaType = it.mediaType,
                            )
                            coroutineScope.launch { modalSheetState.show() }
                        },
                        onFavoriteToggle = { mediaId: String, favorited: Boolean ->
                            when (favorited) {
                                true -> submitAction(SearchAction.AddToFavorites(mediaId.toInt()))
                                false -> submitAction(SearchAction.RemoveFromFavorites(mediaId.toInt()))
                            }
                        },
                    )
                }

                is SearchState.Loading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorResource(id = ColorRes.background.resourceId)),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.secondary
                        )
                    }
                }

                is SearchState.Results -> {
                    println("deadpool - $state")
                    MLTitledMediaGrid(
                        gridTitle = stringResource(id = com.example.medialion.R.string.top_results),
                        media = state.searchResults,
                        suggestedMedia = listOf(
                            stringResource(id = com.example.medialion.R.string.related_movies) to state.relatedTitles[0],
                            stringResource(id = com.example.medialion.R.string.related_series) to state.relatedTitles[1],
                            stringResource(id = com.example.medialion.R.string.related_documentaries) to state.relatedTitles[2],
                        ),
                        onMediaClicked = {
                            selectedMediaItem = SimpleMediaItem(
                                id = it.id.toString(),
                                title = it.title,
                                posterUrl = it.posterUrl,
                                description = it.overview,
                                year = it.releaseYear,
                                mediaType = it.mediaType,
                            )
                            coroutineScope.launch { modalSheetState.show() }
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

            var screenState: SearchState by remember {
                mutableStateOf(SearchState.Loading(""))
            }

            var collectionState: List<Pair<String, List<Int>>> by remember {
                mutableStateOf(emptyList())
            }

            SearchScreen(
                collectionState = collectionState,
                state = screenState,
                submitAction = { action ->
                    when (action) {
                        is SearchAction.AddToFavorites -> TODO()
                        SearchAction.ClearSearchText -> {
                            screenState = SearchState.Idle(
                                "",
                                listOf(
                                    MediaItemUI(id = 6370, title = "posse", isFavorited = false, posterUrl = "http://www.bing.com/search?q=suscipit", bannerUrl = "https://www.google.com/#q=posidonium", genreIds = listOf(), overview = "audire", popularity = 52.53, voteAverage = 54.55, voteCount = 3509, releaseYear = "volutpat", mediaType = MediaType.MOVIE)
                                )
                            )
                        }

                        is SearchAction.RemoveFromFavorites -> TODO()
                        is SearchAction.SubmitSearchQuery -> TODO()
                        is SearchAction.GetMovieDetails -> TODO()
                        is SearchAction.AddToCollection -> TODO()
                        is SearchAction.CreateCollection -> TODO()
                        is SearchAction.RemoveFromCollection -> TODO()
                    }
                },
                backstack = Backstack()
            )
        }
    }
}