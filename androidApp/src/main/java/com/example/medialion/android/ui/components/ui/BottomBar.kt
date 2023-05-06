package com.example.medialion.android.ui.components.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medialion.ColorRes
import com.example.medialion.android.R
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.android.ui.extensions.gradientBackground

@Composable
fun BottomBar(
    modifier: Modifier = Modifier
) {
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
            modifier = modifier
                .size(40.dp),
            )

        Spacer(modifier = modifier.height(5.dp))

        Text(
            text = stringResource(id = com.example.medialion.R.string.bottom_bar_collection),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.primaryVariant

            )
    }

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.slide_orange_cog_icon),
            contentDescription = "",
            modifier = modifier
                .size(40.dp)
            )

        Spacer(modifier = modifier.height(5.dp))

        Text(
            text = stringResource(id = com.example.medialion.R.string.bottom_bar_home),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.primaryVariant
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