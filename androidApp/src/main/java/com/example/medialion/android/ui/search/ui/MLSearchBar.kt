package com.example.medialion.android.ui.search.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medialion.ColorRes
import com.example.medialion.android.R
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.android.ui.extensions.gradientBackground

@Composable
fun MLSearchBar(
    modifier: Modifier = Modifier,
    state: MutableState<TextFieldValue>,
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions
){
    TextField(
        value = state.value,
        onValueChange = {value ->
            state.value = value
        },
        colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colors.secondary),
        textStyle = MaterialTheme.typography.h1,
        modifier = modifier
            .fillMaxWidth()
            .gradientBackground(
                colors = listOf(
                    colorResource(id = ColorRes.primary.resourceId),
                    colorResource(id = ColorRes.primaryVariant.resourceId)
                ),
                angle = 60f
            ),
        placeholder = { Text(
            text = stringResource(id = label),
            color = Color.White.copy(alpha = 0.5f),
            style = MaterialTheme.typography.h1
        )},
        singleLine = true,
        keyboardOptions = keyboardOptions,
        shape = RectangleShape,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "",
                modifier = modifier
                    .padding(15.dp)
                    .size(28.dp))
        },
        trailingIcon = {
            when {
                state.value.text.isNotEmpty() -> Image(
                    painter = painterResource(id = R.drawable.cancel_text),
                    contentDescription = "",
                    modifier = modifier
                        .padding(15.dp)
                        .size(28.dp)
                        .clickable { state.value = TextFieldValue("") }
                )

                }


        }
    )
}

@Preview
@Composable
fun MLSearchBarPreview() {
    MediaLionTheme {
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        Surface(modifier = Modifier.fillMaxSize()) {
            MLSearchBar(
                state = textState,
                label = com.example.medialion.R.string.empty_search,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
        }
    }
}