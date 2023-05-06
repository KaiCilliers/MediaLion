package com.sunrisekcdeveloper.medialion.android.ui.discovery.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme

@Composable
fun FilterCategories(
    modifier: Modifier = Modifier
) {
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
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier
                    .padding(end = 40.dp)
            )
            Text(
                text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.filter_movies),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier
                    .padding(end = 40.dp)
            )


            Text(
                text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.filter_series),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2,

                )
            Text(
                text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.filter_categories),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier
                    .padding(start = 40.dp)
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