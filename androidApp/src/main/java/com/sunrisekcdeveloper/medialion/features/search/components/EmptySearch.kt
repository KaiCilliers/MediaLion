package com.sunrisekcdeveloper.medialion.features.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.utils.StringRes
import com.sunrisekcdeveloper.medialion.theme.MediaLionTheme

@Composable
fun EmptySearch(modifier: Modifier = Modifier) {
  Column(
      modifier = modifier
          .fillMaxSize()
          .background(MaterialTheme.colors.background)
          .padding(top = 16.dp, start = 16.dp, end = 40.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
        Text(
            text = stringResource(id = StringRes.emptyResultTitle.resourceId),
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.secondary
        )
         Text(
             text = stringResource(id = StringRes.emptyResultBody.resourceId),
             style = MaterialTheme.typography.h1,
             color = MaterialTheme.colors.secondary
         )

  }
}

@Preview
@Composable
private fun SearchEmptyStatePreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            EmptySearch()
        }
    }
}