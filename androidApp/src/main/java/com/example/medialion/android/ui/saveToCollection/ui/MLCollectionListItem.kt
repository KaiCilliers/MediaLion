package com.example.medialion.android.ui.saveToCollection.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medialion.android.R
import com.example.medialion.android.theme.MediaLionTheme

@Composable
fun MLCollectionListItem(
    modifier: Modifier = Modifier,
    collectionName: String,
    checkMarkVisible: Boolean,
    onItemClick: (isChecked: Boolean) -> Unit
) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(10.dp)
                .clickable {
                    onItemClick(!checkMarkVisible)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.videoreelplaceholder),
                contentDescription = "",
                modifier = modifier
                    .padding(end = 10.dp)
                    .size(35.dp)
            )
            Text(
                text = collectionName,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.secondaryVariant
            )
            Spacer(modifier = modifier.weight(1F))
            if (checkMarkVisible) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "",
                    modifier = modifier
                        .padding(end = 16.dp)
                        .size(30.dp),
                    tint = MaterialTheme.colors.secondaryVariant,
                )
            }

        }
    }


@Composable
fun MLCollectionTextField(
    textQuery: String,
    labelText: String,
    onTextQueryTextChange: (String) -> Unit,
    onSaveList: () -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text
    )
) {

        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(16.dp)
        ){
            TextField(
                value = textQuery,
                onValueChange = { onTextQueryTextChange(it) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.secondaryVariant,
                    cursorColor = MaterialTheme.colors.secondaryVariant
                ),
                textStyle = MaterialTheme.typography.h1,
                modifier = modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .background(MaterialTheme.colors.onBackground)
                    .size(height = 50.dp, width = 180.dp),
                placeholder = {
                    Text(
                        text = labelText,
                        color = MaterialTheme.colors.secondaryVariant.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.h1
                    )
                },
                singleLine = true,
                keyboardOptions = keyboardOptions,
                shape = RectangleShape,
                trailingIcon = {
                    when {
                        textQuery.isNotEmpty() -> Image(
                            painter = painterResource(id = R.drawable.name_created_icon),
                            contentDescription = "",
                            modifier = modifier
                                .size(30.dp)
                                .clickable { onSaveList() }
                        )

                    }
                }
            )



        }
    }


@Preview
@Composable
private fun MLCollectionListItemPreview() {
    MediaLionTheme {
        var text by remember {
            mutableStateOf("")
        }
        Surface(modifier = Modifier.fillMaxSize()) {
         MLCollectionListItem(collectionName = "Favorite List", checkMarkVisible = true, onItemClick = {})
        }
    }
}