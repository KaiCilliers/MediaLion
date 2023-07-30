package com.sunrisekcdeveloper.medialion.features.discovery.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.android.R

@Composable
fun MLTopBar(
    onSearchIconClicked: () -> Unit,
    onInfoIconClicked: () -> Unit,
    showLogo: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .padding(top = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.search_icon),
            contentDescription = "",
            modifier = Modifier
                .size(30.dp)
                .clickable { onSearchIconClicked() }

        )
        Spacer(modifier = Modifier.weight(1f))
        if(showLogo) {
            Image(
                painter = painterResource(id = R.drawable.logo_icon),
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp)

            )
        } else {
            Text(text = "", modifier = Modifier.size(60.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.about_icon),
            contentDescription = "",
            modifier = Modifier
                .size(30.dp)
                .clickable { onInfoIconClicked() }

        )
    }
}