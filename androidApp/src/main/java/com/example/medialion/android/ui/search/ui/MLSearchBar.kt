package com.example.medialion.android.ui.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medialion.ColorRes
import com.example.medialion.android.R
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.android.ui.extensions.gradientOrange

@Composable
fun MLSearchBar(
    searchQuery: String,
    labelText: String,
    onSearchQueryTextChange: (String) -> Unit,
    onClearSearchText: () -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text
    ),
) {
    TextField(value = searchQuery,
        onValueChange = { onSearchQueryTextChange(it) },
        colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colors.secondary),
        textStyle = MaterialTheme.typography.h1,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .gradientOrange(),
        placeholder = {
            Text(
                text = labelText,
                color = Color.White.copy(alpha = 0.5f),
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
                modifier = modifier
                    .padding(15.dp)
                    .size(28.dp)
            )
        },
        trailingIcon = {
            when {
                searchQuery.isNotEmpty() -> Image(painter = painterResource(id = R.drawable.cancel_text),
                    contentDescription = "",
                    modifier = modifier
                        .padding(15.dp)
                        .size(28.dp)
                        .clickable { onClearSearchText() })
            }
        })
}

@Preview
@Composable
fun MLSearchBarPreview() {
    MediaLionTheme {
        var text by remember {
            mutableStateOf("")
        }
        Surface(modifier = Modifier.fillMaxSize()) {
            MLSearchBar(
                searchQuery = text,
                labelText = stringResource(id = com.example.medialion.R.string.empty_search),
                onSearchQueryTextChange = { text = it },
                onClearSearchText = { text = "" },
            )
        }
    }
}