package com.sunrisekcdeveloper.medialion.android.ui.discovery.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme


enum class FilterCategory { All, MOVIES, SERIES, CATEGORIES }

@Composable
fun MLFilterCategories(
    selectedFilter: FilterCategory,
    onNewSelection: (FilterCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            modifier = modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center

        ) {
            Text(
                text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.filter_all),
                color = if (selectedFilter == FilterCategory.All) {
                    MaterialTheme.colors.onSurface
                } else {
                    MaterialTheme.colors.onSecondary
                },
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier

                    .clickable { onNewSelection(FilterCategory.All) }
            )
            Spacer(modifier = modifier.weight(1f))
            Text(
                text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.filter_movies),
                color = if (selectedFilter == FilterCategory.MOVIES) {
                    MaterialTheme.colors.onSurface
                } else {
                    MaterialTheme.colors.onSecondary
                },
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier

                    .clickable { onNewSelection(FilterCategory.MOVIES) }
            )

            Spacer(modifier = modifier.weight(1f))


            Text(
                text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.filter_series),
                color = if (selectedFilter == FilterCategory.SERIES) {
                    MaterialTheme.colors.onSurface
                } else {
                    MaterialTheme.colors.onSecondary
                },
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier
                    .clickable { onNewSelection(FilterCategory.SERIES) }

            )

            Spacer(modifier = modifier.weight(1f))
            Text(
                text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.filter_categories),
                color = if (selectedFilter == FilterCategory.CATEGORIES) {
                    MaterialTheme.colors.onSurface
                } else {
                    MaterialTheme.colors.onSecondary
                },
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier

                    .clickable { onNewSelection(FilterCategory.CATEGORIES) }
            )
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun FilterCategoriesPreview() {
    MediaLionTheme {
        MLFilterCategories(
            selectedFilter = FilterCategory.All,
            onNewSelection = {}
        )
    }

}