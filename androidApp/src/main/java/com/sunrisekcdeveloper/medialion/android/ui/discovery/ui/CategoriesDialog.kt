package com.sunrisekcdeveloper.medialion.android.ui.discovery.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme

@Composable
fun CategoriesDialog(
    categories : List<CategoriesNames>,
    onDismiss: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = {onDismiss()},
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
                            .clickable {
                                onDismiss()
                            }
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
                            text = it.name,
                            style = MaterialTheme.typography.h4,
                            color = MaterialTheme.colors.secondaryVariant
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
    val categories: List<CategoriesNames> by remember {
        mutableStateOf(
            listOf(
                CategoriesNames(name = "Horror"),
                CategoriesNames(name = "Romance"),
                CategoriesNames(name = "Thriller"),
                CategoriesNames(name = "Crime"),
                CategoriesNames(name = "Comedy"),
                CategoriesNames(name = "Drama"),
                CategoriesNames(name = "Fantasy"),
                CategoriesNames(name = "RomCom"),
                CategoriesNames(name = "Action"),
                CategoriesNames(name = "Adventure"),
                CategoriesNames(name = "Animation"),
                CategoriesNames(name = "Sci-Fi"),
            )
        )
    }
    MediaLionTheme {
        CategoriesDialog(categories = categories)
    }

}