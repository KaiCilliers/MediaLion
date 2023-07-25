package com.sunrisekcdeveloper.medialion.android.ui.saveToCollection.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sunrisekcdeveloper.medialion.R
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.android.ui.components.ui.MLProgress
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.features.shared.Content
import com.sunrisekcdeveloper.medialion.features.shared.FailedToLoadCollections
import com.sunrisekcdeveloper.medialion.features.shared.Loading
import com.sunrisekcdeveloper.medialion.features.shared.MiniCollectionUIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SaveToCollectionScreen(
    onDismiss: () -> Unit,
    targetedMediaItem: SingleMediaItem,
    miniCollectionUIState: MiniCollectionUIState,
    onUpdateCollection: (CollectionNew) -> Unit,
    onCreateCollection: (CollectionNew) -> Unit,
) {
    var text by remember {
        mutableStateOf("")
    }


    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            elevation = 5.dp,
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.75f),
            color = MaterialTheme.colors.onSecondary,
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 18.dp)
                    .background(MaterialTheme.colors.onSecondary),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(id = R.string.save_to_list_title),
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.secondaryVariant,
                    )
                }

                when (miniCollectionUIState) {
                    is Content -> {
                        LazyColumn(
                            modifier = Modifier
                                .size(height = 160.dp, width = 500.dp),
                        ) {
                            items(miniCollectionUIState.collections) { collectionItem ->
                                MLCollectionListItem(
                                    collectionName = collectionItem.title().toString(),
                                    checkMarkVisible = collectionItem.media().contains(targetedMediaItem),
                                    onItemClick = { isChecked ->
                                        if (isChecked) {
                                            collectionItem.add(targetedMediaItem)
                                        } else collectionItem.remove(targetedMediaItem)
                                        onUpdateCollection(collectionItem)
                                    },
                                )
                            }

                        }

                        MLCollectionTextField(
                            textQuery = text,
                            labelText = stringResource(id = R.string.empty_add_to_list_text),
                            onTextQueryTextChange = { text = it },
                            onCreateCollection = {
                                val newCollection = CollectionNew.Def(
                                    name = text,
                                    mediaItem = targetedMediaItem
                                )
                                onCreateCollection(newCollection)
                                text = ""
                            }
                        )

                        Row {
                            Spacer(modifier = Modifier.weight(1f))

                            Button(
                                onClick = { onDismiss() },
                                modifier = Modifier
                                    .background(MaterialTheme.colors.primaryVariant)
                                    .size(width = 80.dp, height = 40.dp),
                                shape = RoundedCornerShape(8.dp),
                                elevation = ButtonDefaults.elevation(
                                    defaultElevation = 6.dp,
                                    pressedElevation = 8.dp
                                )
                            ) {
                                Text(
                                    text = "Done",
                                    color = MaterialTheme.colors.secondary,
                                    style = MaterialTheme.typography.h5,
                                    textAlign = TextAlign.Center
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                    FailedToLoadCollections -> TODO()
                    Loading -> MLProgress()
                }

            }
        }
    }
}

@Preview
@Composable
private fun SaveToCollectionScreenPreview() {
    val savedMovie = SingleMediaItem.Movie("Harry")
    var collections: List<CollectionNew> by remember {
        mutableStateOf(
            listOf(
                CollectionNew.Def(name = "Favorites List", mediaItem = savedMovie),
                CollectionNew.Def(name = "Must Watch"),
                CollectionNew.Def(name = "Watch Again", mediaItem = savedMovie),
                CollectionNew.Def(name = "Horror"),
                CollectionNew.Def(name = "Comedies"),
                CollectionNew.Def(name = "Best of Robbin Williams"),
                CollectionNew.Def(name = "Harry Potter"),
            )
        )
    }
    var showAboutDialog by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            if (showAboutDialog) {
                SaveToCollectionScreen(
                    onDismiss = {
                        scope.launch {
                            showAboutDialog = false
                            delay(1_000)
                            showAboutDialog = true
                        }
                    },
                    targetedMediaItem = savedMovie,
                    miniCollectionUIState = Content(ID.Def("asdad"), collections),
                    onUpdateCollection = { newCollection ->
                        collections.toMutableList().apply {
                            val indexOfMatchingCollection = indexOfFirst { it == newCollection }
                            this[indexOfMatchingCollection] = newCollection
                            collections = this
                        }
                    },
                    onCreateCollection = { newCollection ->
                        collections.toMutableList().apply {
                            add(newCollection)
                            collections = this
                        }
                    },
                )
            }
        }
    }
}