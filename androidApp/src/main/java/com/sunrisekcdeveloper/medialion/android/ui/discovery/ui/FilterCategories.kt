package com.sunrisekcdeveloper.medialion.android.ui.discovery.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme


enum class FilterCategories {All, MOVIES, SERIES, CATEGORIES}

@Composable
fun FilterCategories(
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf(FilterCategories.All) }
    Box (
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ){
        Row (
            modifier = modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center

        ){
            Text(
                text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.filter_all),
                color = if (selectedFilter == FilterCategories.All) {
                    MaterialTheme.colors.onSurface
                } else {
                       MaterialTheme.colors.onSecondary
                       },
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier
                    .padding(end = 40.dp)
                    .clickable {selectedFilter = FilterCategories.All}
            )
            Text(
                text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.filter_movies),
                color = if (selectedFilter == FilterCategories.MOVIES) {
                    MaterialTheme.colors.onSurface
                } else {
                       MaterialTheme.colors.onSecondary
                       },
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier
                    .padding(end = 40.dp)
                    .clickable { selectedFilter = FilterCategories.MOVIES }
            )


            Text(
                text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.filter_series),
                color = if (selectedFilter == FilterCategories.SERIES) {
                    MaterialTheme.colors.onSurface
                }else {
                      MaterialTheme.colors.onSecondary
                      },
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier
                    .clickable { selectedFilter = FilterCategories.SERIES }

                )
            Text(
                text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.filter_categories),
                color = if (selectedFilter == FilterCategories.CATEGORIES) {
                    MaterialTheme.colors.onSurface
                } else {
                       MaterialTheme.colors.onSecondary
                       },
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier
                    .padding(start = 40.dp)
                    .clickable { selectedFilter = FilterCategories.CATEGORIES }
            )
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun FilterCategoriesPreview() {
    MediaLionTheme {
        FilterCategories()
    }

}