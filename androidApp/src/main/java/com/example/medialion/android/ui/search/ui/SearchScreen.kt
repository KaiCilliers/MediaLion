package com.example.medialion.android.ui.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.models.MovieUiModel
import com.example.medialion.search.SearchAction
import com.example.medialion.search.SearchState

@Composable
fun SearchScreen(
    state: SearchState,
    submitAction: (SearchAction) -> Unit,
) {
    Column {
        Text(
            text = "Search Bar goes here",
            fontSize = 60.sp,
            modifier = Modifier.clickable {
                submitAction(SearchAction.ClearSearchText)
            }
        )
        when (state) {
            SearchState.Empty -> {
                Text(text = "Empty Results")
            }
            is SearchState.Idle -> {
                Column {
                    Text(
                        text = "Suggested Media",
                        fontSize = 30.sp
                    )
                    LazyColumn {
                        items(state.suggestedMedia) {
                            Text(
                                text = it.title,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }
            SearchState.Loading -> {
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
                        itemsIndexed(state.relatedTitles) {index, movies ->
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
                mutableStateOf(SearchState.Loading)
            }

            SearchScreen(
                state = screenState,
                submitAction = { action ->
                    when (action) {
                        is SearchAction.AddToFavorites -> TODO()
                        SearchAction.ClearSearchText -> {
                            screenState = SearchState.Idle(
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