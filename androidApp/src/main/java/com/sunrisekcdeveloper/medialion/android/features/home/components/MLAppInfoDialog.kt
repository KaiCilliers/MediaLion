package com.sunrisekcdeveloper.medialion.android.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
// todo rename
fun MLAppInfoDialog(
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            elevation = 5.dp,
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.75f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "One", modifier = Modifier.clickable {
                    onDismiss()
                })
                Text(text = "Two", Modifier.clickable {
                    onDismiss()
                })
            }
        }
    }
}