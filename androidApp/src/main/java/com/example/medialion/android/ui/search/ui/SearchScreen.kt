package com.example.medialion.android.ui.search.ui

import android.view.MotionEvent
import android.widget.Toast
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
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
import com.example.medialion.domain.components.search.SearchAction
import com.example.medialion.domain.components.search.SearchState
import com.example.medialion.domain.models.MovieUiModel
import com.zhuinden.simplestack.Backstack

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    state: SearchState,
    submitAction: (SearchAction) -> Unit,
    backstack: Backstack,
) {

    val context = LocalContext.current
    var showAboutDialog by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    if (showAboutDialog) {
        AboutScreen { showAboutDialog = false }
    }

    Column(
        Modifier
            .background(MaterialTheme.colors.background)
            .blur(radius = if (showAboutDialog) 10.dp else 0.dp)
            .pointerInteropFilter {
                when(it.action) {
                    MotionEvent.ACTION_DOWN,
                    MotionEvent.ACTION_MOVE,
                    MotionEvent.ACTION_UP -> {
                        keyboardController?.hide()
                    }
                    else -> {}
                }
                false
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
                    .size(20.dp)
                    .clickable {
                        Toast
                            .makeText(context, "Navigate back functionality...", Toast.LENGTH_SHORT)
                            .show()
                    },
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.about_icon),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .clickable { showAboutDialog = true }
            )
        }

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
                    rowTitle = "Suggested Media",
                    movies = state.suggestedMedia,
                    onMediaClicked = {
                        Toast.makeText(context, "${it.title} was clicked!", Toast.LENGTH_SHORT)
                            .show()
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
                        color = Color.White
                    )
                }
            }

            is SearchState.Results -> {
                println("deadpool - $state")
                MLTitledMediaGrid(
                    gridTitle = "Results",
                    movies = state.searchResults,
                    suggestedMedia = listOf(
                        "Suggested Media #1" to state.relatedTitles[0],
                        "Suggested Media #2" to state.relatedTitles[1],
                        "Suggested Media #3" to state.relatedTitles[2],
                    )
                )
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

            SearchScreen(
                state = screenState,
                submitAction = { action ->
                    when (action) {
                        is SearchAction.AddToFavorites -> TODO()
                        SearchAction.ClearSearchText -> {
                            screenState = SearchState.Idle(
                                "",
                                listOf(
                                    MovieUiModel(1, "Title", true),
                                    MovieUiModel(1, "Title", true),
                                    MovieUiModel(1, "Title", true),
                                    MovieUiModel(1, "Title", true),
                                    MovieUiModel(1, "Title", true),
                                    MovieUiModel(1, "Title", true),
                                )
                            )
                        }

                        is SearchAction.RemoveFromFavorites -> TODO()
                        is SearchAction.SubmitSearchQuery -> TODO()
                    }
                },
                backstack = Backstack()
            )
        }
    }
}