package com.sunrisekcdeveloper.medialion.android.ui.components.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.ColorRes
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.android.ui.extensions.gradientBackground

enum class BottomBar {HOME, COLLECTION}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier
) {
    var selectedScreen by remember {mutableStateOf(BottomBar.HOME)}
  Card (
modifier = modifier
    .fillMaxWidth()
    .gradientBackground(
        colors = listOf(
            colorResource(id = ColorRes.primary.resourceId),
            colorResource(id = ColorRes.primaryVariantBlue.resourceId)
        ),
        angle = -90f
    ),
      shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
  ){
Row (
    modifier = modifier
        .gradientBackground(
            colors = listOf(
                colorResource(id = ColorRes.primary.resourceId),
                colorResource(id = ColorRes.primaryVariantBlue.resourceId)
            ),
            angle = -90f
        )
        .padding(horizontal = 60.dp, vertical = 16.dp),
    horizontalArrangement = Arrangement.SpaceBetween,


){
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        Image(
            painter = painterResource(id = R.drawable.home_icon),
            contentDescription = "",
            colorFilter = if (selectedScreen == BottomBar.HOME) {
                ColorFilter.tint(MaterialTheme.colors.onSecondary)
            } else {
                   ColorFilter.tint(MaterialTheme.colors.primaryVariant)
                   },
            modifier = modifier
                .size(40.dp)
                .clickable { selectedScreen = BottomBar.HOME},
            )

        Spacer(modifier = modifier.height(5.dp))

        Text(
            text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.bottom_bar_collection),
            style = MaterialTheme.typography.h1,
            color = if (selectedScreen == BottomBar.HOME) {
                MaterialTheme.colors.onSecondary
            } else {
                   MaterialTheme.colors.primaryVariant
                   },
            modifier = modifier
                .clickable { selectedScreen = BottomBar.HOME }

            )
    }

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.slide_orange_cog_icon),
            contentDescription = "",
            colorFilter = if (selectedScreen == BottomBar.COLLECTION) {
                ColorFilter.tint(MaterialTheme.colors.onSecondary)
            } else {
                   ColorFilter.tint(MaterialTheme.colors.primaryVariant)
                   },
            modifier = modifier
                .size(40.dp)
                .clickable { selectedScreen = BottomBar.COLLECTION }
            )

        Spacer(modifier = modifier.height(5.dp))

        Text(
            text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.bottom_bar_home),
            style = MaterialTheme.typography.h1,
            color = if (selectedScreen == BottomBar.COLLECTION) {
                MaterialTheme.colors.onSecondary
            } else {
                   MaterialTheme.colors.primaryVariant
                   },
            modifier = modifier
                .clickable { selectedScreen = BottomBar.COLLECTION }
            )
    }
}
  }
}

@Preview
@Composable
private fun BottomBarPreview() {
    MediaLionTheme {
        BottomBar()
    }

}