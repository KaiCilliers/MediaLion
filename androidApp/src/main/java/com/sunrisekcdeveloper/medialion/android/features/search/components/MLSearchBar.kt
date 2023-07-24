package com.sunrisekcdeveloper.medialion.android.features.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.android.ui.extensions.gradientOrange
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.SearchQuery

@Composable
fun MLSearchBar(
    searchQuery: SearchQuery,
    labelText: String,
    modifier: Modifier = Modifier,
    onSearchQueryTextChange: (SearchQuery) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text
    ),
) {
    TextField(
        value = searchQuery.toString(),
        onValueChange = { searchText ->
            searchQuery.update(searchText)
            onSearchQueryTextChange(searchQuery)
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.secondary
        ),
        textStyle = MaterialTheme.typography.h1,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .gradientOrange(),
        placeholder = {
            Text(
                text = labelText,
                color = MaterialTheme.colors.secondary.copy(alpha = 0.5f),
                style = MaterialTheme.typography.h1
            )
        },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        shape = RectangleShape,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(28.dp)
            )
        },
        trailingIcon = {
            when {
                searchQuery.toString().isNotEmpty() -> Image(painter = painterResource(id = R.drawable.cancel_text),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(28.dp)
                        .clickable {
                            searchQuery.clear()
                            onSearchQueryTextChange(searchQuery)
                        })
            }
        })
}

@Preview
@Composable
private fun MLSearchBarPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

            var searchQuery: SearchQuery = SearchQuery.Default("")

            MLSearchBar(
                searchQuery = searchQuery,
                labelText = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.empty_search),
                onSearchQueryTextChange = { searchQuery = it },
            )
        }
    }
}