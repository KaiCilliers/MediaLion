package com.sunrisekcdeveloper.medialion.android.features.search

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.android.features.home.GlobalRouter
import com.sunrisekcdeveloper.medialion.android.features.search.components.MLSearchBar
import com.sunrisekcdeveloper.medialion.android.features.search.components.EmptySearch
import com.sunrisekcdeveloper.medialion.android.features.search.components.SearchResults
import com.sunrisekcdeveloper.medialion.android.features.search.components.TopSuggestions
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.android.ui.components.ui.MLProgress
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.SearchQuery
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.MediaWithTitle
import com.sunrisekcdeveloper.medialion.features.search.SearchScreenAction
import com.sunrisekcdeveloper.medialion.features.search.SearchUIState
import com.sunrisekcdeveloper.medialion.features.shared.Content
import com.sunrisekcdeveloper.medialion.features.shared.MiniCollectionUIState
import com.sunrisekcdeveloper.medialion.features.shared.UpdateCollection
import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.utils.StringRes
import com.sunrisekcdeveloper.medialion.utils.rememberService
import com.zhuinden.simplestackcomposeintegration.core.LocalBackstack
import io.github.aakira.napier.Napier

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun SearchScreen() {
    val backstack = LocalBackstack.current
    val context = LocalContext.current

    val globalRouter = rememberService<GlobalRouter>()
    val searchViewModel = rememberService<SearchViewModel>()

    val searchState by searchViewModel.searchScreenState.collectAsState()
    val miniCollectionState by searchViewModel.miniCollectionState.collectAsState()

//    var showCollectionDialog by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current


    SearchScreenContent(
        keyboardController = keyboardController,
        searchState = searchState,
        miniCollectionState = miniCollectionState,
        navigateBack = { backstack.goBack() },
        openInfoDialog = { globalRouter.infoDialogRouter.show() },
        openMediaPreviewSheet = { mediaItem -> globalRouter.detailPreviewSheetRouter.show(mediaItem) },
        submitSearchQuery = { newQuery -> searchViewModel.submit(SearchScreenAction.SubmitSearchQuery(newQuery)) },
        updateFavorites = { newCollection -> searchViewModel.submit(UpdateCollection(newCollection)) }
    )

//    if (showCollectionDialog) {
//        SaveToCollectionScreen(
//            onDismiss = { showCollectionDialog = false },
//            collections = collectionState
//                .map { it.name to it.contents.map { it.id.value } }
//                .map {
//                    val selectedMedia = selectedMediaItem
//                    val checked = if (selectedMedia != null) {
//                        it.second.contains(selectedMedia.id.toInt())
//                    } else false
//                    CollectionItem(it.first.value, checked)
//                },
//            onCollectionItemClicked = { collectionName -> },
//            onAddToCollection = { collectionName ->
//                val selectedMedia = selectedMediaItem
//                if (selectedMedia != null) {
//                    submitSearchAction(SearchAction.AddToCollection(Title(collectionName), ID(selectedMedia.id.toInt()), selectedMedia.mediaType))
//                }
//            },
//            onRemoveFromCollection = { collectionName ->
//                val selectedMedia = selectedMediaItem
//                if (selectedMedia != null) {
//                    submitSearchAction(SearchAction.RemoveFromCollection(Title(collectionName), ID(selectedMedia.id.toInt()), selectedMedia.mediaType))
//                }
//            },
//            onSaveList = {
//                submitSearchAction(SearchAction.CreateCollection(Title(it)))
//            }
//        )
//    }
//

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreenContent(
    keyboardController: SoftwareKeyboardController?,
    searchState: SearchUIState,
    miniCollectionState: MiniCollectionUIState,
    navigateBack: () -> Unit,
    openInfoDialog: () -> Unit,
    openMediaPreviewSheet: (MediaItemUI) -> Unit,
    submitSearchQuery: (SearchQuery) -> Unit,
    updateFavorites: (CollectionNew) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .background(MaterialTheme.colors.background)
            // todo fix tapping away keyboard also interacts with UI successfully
            .pointerInteropFilter {
                // comment explaining what this does, OR create an extension on modifier with better name
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
                    .clickable { navigateBack() },
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.about_icon),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .clickable { openInfoDialog() }
            )
        }

        MLSearchBar(
            searchQuery = searchState.searchQuery,
            labelText = stringResource(id = StringRes.emptySearch.resourceId),
            onSearchQueryTextChange = { latestQuery -> submitSearchQuery(latestQuery) },
        )
        when (searchState) {
            is SearchUIState.Loading -> MLProgress()
            is SearchUIState.NoResults -> EmptySearch()
            is SearchUIState.Results -> {
                val results = searchState.results.results()
                SearchResults(
                    gridTitle = results.title().toString(),
                    media = results.media().map { MediaItemUI.from(it) },
                    onMediaClicked = { mediaItem -> openMediaPreviewSheet(mediaItem) },
                )
            }

            is SearchUIState.TopSuggestions -> {
                TopSuggestions(
                    rowTitle = stringResource(id = StringRes.topSuggestions.resourceId),
                    media = searchState.media.map { MediaItemUI.from(it) },
                    onMediaClicked = { mediaItem -> openMediaPreviewSheet(mediaItem) },
                    onFavoriteToggle = { mediaItem: MediaItemUI, favorited: Boolean ->
                        val collections = miniCollectionState
                        if (collections is Content) {
                            val favoriteCollection = collections.collections.find { it.title() == Title("Favorites") }

                            when (favorited) {
                                true -> favoriteCollection?.add(mediaItem.toDomain())
                                false -> favoriteCollection?.remove(mediaItem.toDomain())
                            }

                            favoriteCollection?.let { updateFavorites(it) }
                        }
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun SearchScreenContentPreview() {
    MediaLionTheme {

        val searchState = SearchUIState.TopSuggestions(
            media = listOf()
        )
        val miniCollectionState = Content(listOf(CollectionNew.Def("Favorites")))
        val keyboardController = LocalSoftwareKeyboardController.current

        Surface(modifier = Modifier.fillMaxSize()) {
            SearchScreenContent(
                keyboardController = keyboardController,
                searchState = searchState,
                miniCollectionState = miniCollectionState,
                navigateBack = {},
                openInfoDialog = {},
                openMediaPreviewSheet = {},
                submitSearchQuery = {},
                updateFavorites = {}
            )
        }
    }
}