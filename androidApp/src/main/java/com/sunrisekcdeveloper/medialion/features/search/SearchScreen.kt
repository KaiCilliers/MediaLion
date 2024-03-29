package com.sunrisekcdeveloper.medialion.features.search

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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.features.search.components.SearchResults
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.SearchQuery
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID
import com.sunrisekcdeveloper.medialion.features.home.GlobalRouter
import com.sunrisekcdeveloper.medialion.features.search.components.EmptySearch
import com.sunrisekcdeveloper.medialion.features.search.components.MLSearchBar
import com.sunrisekcdeveloper.medialion.features.search.components.TopSuggestions
import com.sunrisekcdeveloper.medialion.features.shared.Content
import com.sunrisekcdeveloper.medialion.features.shared.MLProgress
import com.sunrisekcdeveloper.medialion.features.shared.MiniCollectionUIState
import com.sunrisekcdeveloper.medialion.features.shared.UpdateCollection
import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.utils.StringRes
import com.sunrisekcdeveloper.medialion.utils.rememberService
import com.zhuinden.simplestackcomposeintegration.core.LocalBackstack

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen() {
    val backstack = LocalBackstack.current

    val globalRouter = rememberService<GlobalRouter>()
    val searchViewModel = rememberService<SearchViewModel>()

    val searchState by searchViewModel.searchScreenState.collectAsState()
    val miniCollectionState by searchViewModel.miniCollectionState.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    SearchScreenContent(
        keyboardController = keyboardController,
        searchState = searchState,
        miniCollectionState = miniCollectionState,
        navigateBack = { backstack.goBack() },
        openInfoDialog = { globalRouter.infoRouter.show() },
        openMediaPreviewSheet = { mediaItem -> globalRouter.mediaPreviewRouter.show(mediaItem) },
        submitSearchQuery = { newQuery -> searchViewModel.submit(SearchScreenAction.SubmitSearchQuery(newQuery)) },
        updateFavorites = { newCollection -> searchViewModel.submit(UpdateCollection(newCollection)) }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchScreenContent(
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

    val currentSearchQuery: String by rememberSaveable {
        mutableStateOf("")
    }

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
                    mediaWithFavorites = searchState.media.map { mediaItemWithFavorite ->
                        Pair(MediaItemUI.from(mediaItemWithFavorite.mediaItem), mediaItemWithFavorite.favorited)
                    },
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
private fun SearchScreenContentPreview() {
    MediaLionTheme {

        val searchState = SearchUIState.TopSuggestions(
            media = listOf()
        )
        val miniCollectionState = Content(ID.Def("ada"), listOf(CollectionNew.Def("Favorites")))
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