package com.example.medialion.android.ui.saveToCollection.ui

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
import androidx.compose.material.Card
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
import com.example.medialion.R
import com.example.medialion.android.theme.MediaLionTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SaveToCollectionScreen(
    onDismiss: () -> Unit,
    collections: List<CollectionItem>,
    onCollectionItemClicked: (String) -> Unit,
    onAddToCollection: (String) -> Unit = {},
    onRemoveFromCollection: (String) -> Unit = {},
    onSaveList: (String) -> Unit,
) {
    var text by remember {
        mutableStateOf("")
    }


    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            elevation = 5.dp,
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.75f),
            backgroundColor = MaterialTheme.colors.onSecondary
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

                LazyColumn(
                    modifier = Modifier
                        .size(height = 160.dp, width = 500.dp),
                ) {
                    items(collections) { collectionItem ->
                        MLCollectionListItem(
                            collectionName = collectionItem.name,
                            checkMarkVisible = collectionItem.checked,
                            onItemClick = {
                                if (it) {
                                    onAddToCollection(collectionItem.name)
                                } else onRemoveFromCollection(collectionItem.name)
                            },
                        )
                    }

                }

                MLCollectionTextField(
                    textQuery = text,
                    labelText = stringResource(id = R.string.empty_add_to_list_text),
                    onTextQueryTextChange = { text = it },
                    onSaveList = { onSaveList(text); text = "" })

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
        }
    }
}

@Preview
@Composable
private fun SaveToCollectionScreenPreview() {
    var collections: List<CollectionItem> by remember {
        mutableStateOf(
            listOf(
                CollectionItem(name = "Favorites List", checked = false),
                CollectionItem(name = "Must Watch", checked = false),
                CollectionItem(name = "Watch Again", checked = false),
                CollectionItem(name = "Horror", checked = false),
                CollectionItem(name = "Comedies", checked = false),
                CollectionItem(name = "Best of Robbin Williams", checked = false),
                CollectionItem(name = "Harry Potter", checked = false),
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
                    collections = collections,
                    onCollectionItemClicked = { collectionName ->
                        val listCopy = collections.toMutableList()
                        val mediaIndex = listCopy.indexOfFirst { it.name == collectionName }
                        listCopy[mediaIndex] =
                            listCopy[mediaIndex].copy(checked = !listCopy[mediaIndex].checked)

                        collections = listCopy
                    },
                    onSaveList = {
                        val listCopy = collections.toMutableList()
                        listCopy.add(CollectionItem(it, true))
                        collections = listCopy
                    }
                )
            }
        }
    }
}