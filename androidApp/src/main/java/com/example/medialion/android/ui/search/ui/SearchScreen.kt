package com.example.medialion.android.ui.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.medialion.StringRes
import com.example.medialion.android.R
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.domain.models.MovieUiModel
import com.example.medialion.domain.components.search.SearchAction
import com.example.medialion.domain.components.search.SearchState

@Composable
fun SearchScreen(
    state: SearchState,
    submitAction: (SearchAction) -> Unit,
) {
    Column(
        Modifier.background(Color.White)
    ) {

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
                Column {
                    Text(
                        text = "Suggested Media",
                        fontSize = 30.sp
                    )
                    LazyColumn {
                        items(state.suggestedMedia) { suggestedMovie ->
                            Row {
                                Text(
                                    text = suggestedMovie.title,
                                    fontSize = 24.sp
                                )
                                Image(
                                    painter = if (suggestedMovie.isFavorited) {
                                        painterResource(id = R.drawable.heart_filled_icon)
                                    } else {
                                        painterResource(R.drawable.heart_outline_icon)
                                    },
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        if (suggestedMovie.isFavorited) {
                                            submitAction(
                                                SearchAction.RemoveFromFavorites(
                                                    suggestedMovie.id
                                                )
                                            )
                                        } else {
                                            submitAction(SearchAction.AddToFavorites(suggestedMovie.id))
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }

            is SearchState.Loading -> {
                CircularProgressIndicator()
            }

            is SearchState.Results -> {
                Column {
                    Text(
                        text = "Results",
                        fontSize = 30.sp
                    )
                    LazyColumn {
                        items(state.searchResults) {
                            Text(
                                text = it.title,
                                fontSize = 24.sp
                            )
                        }
                        itemsIndexed(state.relatedTitles) { index, movies ->
                            Text(
                                text = "Related Media #$index",
                                fontSize = 30.sp
                            )
                            LazyRow {
                                items(movies) {
                                    Text(
                                        text = it.title,
                                        fontSize = 24.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
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
                }
            )
        }
    }
}