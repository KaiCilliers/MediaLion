package com.sunrisekcdeveloper.medialion.android.features.discovery.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.oldArch.domain.MediaType
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Genre

@Composable
fun CategoriesDialog(
    categories : List<Genre>,
    onDismiss: () -> Unit,
    onSelection: (Genre) -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Card (
            elevation = 5.dp,
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.75f),
            backgroundColor = MaterialTheme.colors.onSecondary
        ){
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 18.dp)
                    .background(MaterialTheme.colors.onSecondary),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Top
                ){
                    Text(
                        text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.categories),
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.secondaryVariant,
                    )

                    Spacer(modifier = Modifier.width(50.dp))

                    Image(
                        painter = painterResource(id = R.drawable.close_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { onDismiss() }
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .size(height = 160.dp, width = 500.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) {
                        Text(
                            text = "${it.name} - ${it.mediaType}",
                            style = MaterialTheme.typography.h4,
                            color = MaterialTheme.colors.secondaryVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelection(it)
                                    onDismiss()
                                }
                        )
                    }

                }
            }
        }
    }
}

@Preview
@Composable
private fun CategoriesDialogPreview() {
    val categories: List<Genre> by remember {
        mutableStateOf(
            listOf(
                Genre(id = 1, name = "Horror", mediaType = MediaType.TV),
                Genre(id = 1, name = "Romance", mediaType = MediaType.TV),
                Genre(id = 1, name = "Thriller", mediaType = MediaType.MOVIE),
                Genre(id = 1, name = "Crime", mediaType = MediaType.MOVIE),
                Genre(id = 1, name = "Comedy", mediaType = MediaType.MOVIE),
                Genre(id = 1, name = "Drama", mediaType = MediaType.MOVIE),
                Genre(id = 1, name = "Fantasy", mediaType = MediaType.MOVIE),
                Genre(id = 1, name = "RomCom", mediaType = MediaType.MOVIE),
                Genre(id = 1, name = "Action", mediaType = MediaType.MOVIE),
                Genre(id = 1, name = "Adventure", mediaType = MediaType.MOVIE),
                Genre(id = 1, name = "Animation", mediaType = MediaType.MOVIE),
                Genre(id = 1, name = "Sci-Fi", mediaType = MediaType.MOVIE),
            )
        )
    }
    MediaLionTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            CategoriesDialog(
                categories = categories,
                onDismiss = {},
                onSelection = {}
            )
        }
    }

}