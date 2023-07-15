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

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.oldArch.ColorRes
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.android.ui.extensions.gradientBackground

enum class BottomBarOption { DISCOVERY, COLLECTION }

@Composable
fun BottomBar(
    selectedTab: BottomBarOption,
    onNewSelection: (BottomBarOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
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
    ) {
        Row(
            modifier = modifier
                .gradientBackground(
                    colors = listOf(
                        colorResource(id = ColorRes.primary.resourceId),
                        colorResource(id = ColorRes.primaryVariantBlue.resourceId)
                    ),
                    angle = -90f
                )
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,


            ) {

            Spacer(modifier = modifier.weight(1f))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {
                    onNewSelection(BottomBarOption.DISCOVERY)
                }

            ) {


                Image(
                    painter = painterResource(id = R.drawable.home_icon),
                    contentDescription = "",
                    colorFilter = if (selectedTab == BottomBarOption.DISCOVERY) {
                        ColorFilter.tint(MaterialTheme.colors.onSecondary)
                    } else {
                        ColorFilter.tint(MaterialTheme.colors.primaryVariant)
                    },
                    modifier = modifier
                        .size(20.dp),
                )

                Spacer(modifier = modifier.height(5.dp))

                Text(
                    text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.bottom_bar_home),
                    style = MaterialTheme.typography.h1,
                    color = if (selectedTab == BottomBarOption.DISCOVERY) {
                        MaterialTheme.colors.onSecondary
                    } else {
                        MaterialTheme.colors.primaryVariant
                    },
                    modifier = modifier

                )
            }

            Spacer(modifier = modifier.weight(1f))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {
                    onNewSelection(BottomBarOption.COLLECTION)
                }

            ) {
                Image(
                    painter = painterResource(id = R.drawable.slide_orange_cog_icon),
                    contentDescription = "",
                    colorFilter = if (selectedTab == BottomBarOption.COLLECTION) {
                        ColorFilter.tint(MaterialTheme.colors.onSecondary)
                    } else {
                        ColorFilter.tint(MaterialTheme.colors.primaryVariant)
                    },
                    modifier = modifier
                        .size(20.dp)
                )

                Spacer(modifier = modifier.height(5.dp))

                Text(
                    text = stringResource(id = com.sunrisekcdeveloper.medialion.R.string.bottom_bar_collection),
                    style = MaterialTheme.typography.h1,
                    color = if (selectedTab == BottomBarOption.COLLECTION) {
                        MaterialTheme.colors.onSecondary
                    } else {
                        MaterialTheme.colors.primaryVariant
                    }
                )
            }

            Spacer(modifier = modifier.weight(1f))

        }
    }
}

@Preview
@Composable
private fun BottomBarPreview() {
    MediaLionTheme {
        BottomBar(
            selectedTab = BottomBarOption.DISCOVERY,
            onNewSelection = {}
        )
    }

}