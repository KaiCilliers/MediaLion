package com.sunrisekcdeveloper.medialion.android.features.root.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.android.features.collections.PreviewWrapper
import com.sunrisekcdeveloper.medialion.android.features.search.components.MLMediaPoster
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title

@Composable
fun MLCollectionDetail(
    collection: CollectionNew,
    updateCollection: (CollectionNew) -> Unit,
    showMediaPreviewSheet: (SingleMediaItem) -> Unit,
    modifier: Modifier = Modifier,
) {

    var editMode by remember { mutableStateOf(false) }
    var newTitleValue by remember { mutableStateOf(collection.title().toString()) }
    var modifiableCollection = collection

    Box(modifier = Modifier) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(),
            contentPadding = PaddingValues(22.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item(
                span = { GridItemSpan(3) }
            ) {
                // Favorites collection can not be renamed
                if (!editMode || collection.title().toString() == "Favorites") {
                    Text(
                        text = collection.title().toString(),
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.secondary,
                        modifier = modifier.padding(top = 8.dp, bottom = 6.dp)
                    )
                } else {
                    TextField(
                        value = newTitleValue,
                        onValueChange = { newText -> newTitleValue = newText },
                        modifier = Modifier.padding(top = 8.dp, bottom = 6.dp),
                        textStyle = MaterialTheme.typography.h3,
                    )
                }
            }
            items(collection.media()) { mediaItem ->
                val uiItem = MediaItemUI.from(mediaItem)
                Box {
                    MLMediaPoster(
                        posterUrl = uiItem.posterUrl,
                        title = Title(uiItem.title),
                        modifier = Modifier.clickable { showMediaPreviewSheet(mediaItem) }
                    )
                    if (editMode) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_boxed_x),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.Red),
                            modifier = modifier
                                .size(60.dp)
                                .clickable {
                                    modifiableCollection.run {
                                        remove(mediaItem)
                                        updateCollection(this)
                                    }
                                }
                                .align(Alignment.Center),
                        )
                    }
                }
            }
        }

        val fabIcon = if (editMode) R.drawable.name_created_icon else R.drawable.edit_icon

        Image(
            painter = painterResource(id = fabIcon),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 20.dp, end = 20.dp)
                .size(69.dp)
                .clickable {
                    editMode = !editMode
                    if (!editMode) {
                        // this logic should be present in the Title object
                        if (newTitleValue.trim().isNotEmpty()) {
                            modifiableCollection = modifiableCollection.rename(Title(newTitleValue))
                            updateCollection(modifiableCollection)
                        } else {
                            newTitleValue = collection.title().toString()
                        }
                    }
                }
                .align(Alignment.BottomEnd)
        )
    }
}

@Preview
@Composable
private fun MLCollectionDetailPreview() {
    PreviewWrapper {
        MLCollectionDetail(
            showMediaPreviewSheet = {},
            updateCollection = {},
            collection = CollectionNew.Def(
                "Collection Name", media = listOf(
                    SingleMediaItem.Movie("Movie #1"),
                    SingleMediaItem.Movie("Movie #2"),
                    SingleMediaItem.Movie("Movie #3"),
                    SingleMediaItem.Movie("Movie #4"),
                    SingleMediaItem.Movie("Movie #5"),
                    SingleMediaItem.Movie("Movie #6"),
                    SingleMediaItem.Movie("Movie #7"),
                    SingleMediaItem.Movie("Movie #8"),
                    SingleMediaItem.Movie("Movie #9"),
                )
            )
        )
    }
}