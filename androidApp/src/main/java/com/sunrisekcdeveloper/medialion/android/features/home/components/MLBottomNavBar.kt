package com.sunrisekcdeveloper.medialion.android.features.home.components

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
import com.sunrisekcdeveloper.medialion.utils.ColorRes
import com.sunrisekcdeveloper.medialion.android.R
import com.sunrisekcdeveloper.medialion.android.features.home.HomeDestinations
import com.sunrisekcdeveloper.medialion.android.theme.MediaLionTheme
import com.sunrisekcdeveloper.medialion.utils.gradientBackground

@Composable
fun MLBottomNavBar(
    selectedTab: HomeDestinations,
    onDestinationChange: (HomeDestinations) -> Unit,
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
                    onDestinationChange(HomeDestinations.Discovery)
                }

            ) {


                Image(
                    painter = painterResource(id = R.drawable.home_icon),
                    contentDescription = "",
                    colorFilter = if (selectedTab == HomeDestinations.Discovery) {
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
                    color = if (selectedTab == HomeDestinations.Discovery) {
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
                    onDestinationChange(HomeDestinations.Collections)
                }

            ) {
                Image(
                    painter = painterResource(id = R.drawable.slide_orange_cog_icon),
                    contentDescription = "",
                    colorFilter = if (selectedTab == HomeDestinations.Collections) {
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
                    color = if (selectedTab == HomeDestinations.Collections) {
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
        MLBottomNavBar(
            selectedTab = HomeDestinations.Discovery,
            onDestinationChange = {}
        )
    }

}