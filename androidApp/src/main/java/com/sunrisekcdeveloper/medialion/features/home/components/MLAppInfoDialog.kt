package com.sunrisekcdeveloper.medialion.features.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sunrisekcdeveloper.medialion.R
import com.sunrisekcdeveloper.medialion.theme.MediaLionTheme

@Composable
fun MLAppInfoDialog(
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Card(
            elevation = 5.dp,
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.75f),
            backgroundColor = MaterialTheme.colors.onSecondary
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 18.dp)
                    .background(MaterialTheme.colors.onSecondary),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = stringResource(id = R.string.about),
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.secondaryVariant,
                    )

                    // todo replace hardcoded spacing width
                    Spacer(modifier = Modifier.width(90.dp))

                    Image(
                        painter = painterResource(id = com.sunrisekcdeveloper.medialion.android.R.drawable.close_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .clickable {
                                onDismiss()
                            }
                            .size(30.dp)
                    )
                }
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(id = R.string.about_version),
                        modifier = Modifier.padding(bottom = 10.dp),
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.secondaryVariant,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Text(
                    text = stringResource(id = R.string.about_heading),
                    modifier = Modifier.padding(bottom = 5.dp),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.secondaryVariant
                )
                Text(
                    text = stringResource(id = R.string.app_description),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondaryVariant
                )
                Text(
                    text = stringResource(id = R.string.app_developers),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondaryVariant
                )
                Text(
                    text = stringResource(id = R.string.app_developer_names),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondaryVariant
                )
                Text(
                    text = stringResource(id = R.string.graphic_designer),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondaryVariant
                )
                Text(
                    text = stringResource(id = R.string.graphic_designer_name),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondaryVariant
                )
            }
        }
    }
}

@Preview
@Composable
private fun MLAppInfoDialogPreview() {
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MLAppInfoDialog(
                onDismiss = {}
            )
        }
    }
}