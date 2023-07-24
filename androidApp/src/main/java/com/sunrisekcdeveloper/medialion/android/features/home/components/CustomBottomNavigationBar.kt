package com.sunrisekcdeveloper.medialion.android.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sunrisekcdeveloper.medialion.android.features.home.HomeDestinations

@Composable
fun BottomNav(
    onDestinationChange: (HomeDestinations) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(fontWeight = FontWeight.Bold, text = "Root")

            Spacer(modifier = Modifier.height(16.dp))
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    onDestinationChange(HomeDestinations.Discovery)
                },
                content = {
                    Text("Discovery")
                },
            )
            Button(
                onClick = {
                    onDestinationChange(HomeDestinations.Collections)
                },
                content = {
                    Text("Collections")
                },
            )
        }
    }
}