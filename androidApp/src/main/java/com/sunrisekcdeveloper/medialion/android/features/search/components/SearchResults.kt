package com.sunrisekcdeveloper.medialion.android.features.search.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI

@Composable
fun SearchResults(
    gridTitle: String,
    media: List<MediaItemUI>,
    onMediaClicked: (MediaItemUI) -> Unit,
    modifier: Modifier = Modifier,
) {
    MLTitledMediaGrid(
        gridTitle = gridTitle,
        media = media,
        suggestedMedia = listOf(),
        onMediaClicked = onMediaClicked,
        modifier = modifier
    )
}