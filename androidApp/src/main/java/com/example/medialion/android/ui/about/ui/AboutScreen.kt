package com.example.medialion.android.ui.about.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModel
import com.example.medialion.android.R
import com.example.medialion.android.theme.MediaLionTheme

@Composable
fun AboutScreen(
    viewModal: MainViewModal,
    modifier: Modifier = Modifier
) {
Box (
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
){
    Image(
        painter = painterResource(id = R.drawable.about_icon),
        contentDescription = "",
        modifier = modifier
            .clickable {
                viewModal.onBuyClick()
            }
            .background(MaterialTheme.colors.background)
    )
}
    if (viewModal.isDialogShown) {
        CustomDialog (
            onDismiss = {
                viewModal.onDismissDialog()
            }
        )
    }
}

@Composable
private fun CustomDialog(
    onDismiss: () -> Unit
) {
Dialog(onDismissRequest = {
    onDismiss()
},
    properties = DialogProperties(
        usePlatformDefaultWidth = false
    )
) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth(0.75f),
        backgroundColor = MaterialTheme.colors.onSecondary
    ) {

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 18.dp)
                .background(MaterialTheme.colors.onSecondary),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ){

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ){
                Text(
                    text = "About",
                    style = MaterialTheme.typography.h5,
                    color = Color.Black,
                )
                
                Spacer(modifier = Modifier.width(90.dp))

                Image(
                    painter = painterResource(id = R.drawable.close_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            onDismiss()
                        }
                        .size(30.dp)
                )
            }
            Text(
                text = "MediaLion Information",
                modifier = Modifier.padding(bottom = 5.dp),
                style = MaterialTheme.typography.h4,
                color = Color.Black
            )
            Text(
                text = "This App is a media organiser, which allows users to save and store their favorite movies, series and documentaries in custom lists.",
                style = MaterialTheme.typography.body2,
                color = Color.Black
            )
            Text(
                text = "App Developers:",
                style = MaterialTheme.typography.body2,
                color = Color.Black
            )
            Text(
                text = "Kai Cilliers & Nadine Cilliers",
                style = MaterialTheme.typography.body2,
                color = Color.Black
            )
            Text(
                text = "Graphics Designer:",
                style = MaterialTheme.typography.body2,
                color = Color.Black
            )
            Text(
                text = "Roxie Nemes",
                style = MaterialTheme.typography.body2,
                color = Color.Black
            )
        }
    }
}
}

class MainViewModal : ViewModel() {
var isDialogShown by mutableStateOf(false)
    private set

    fun onBuyClick() {
        isDialogShown = true
    }
    fun onDismissDialog () {
        isDialogShown = false
    }
}

@Preview
@Composable
private fun AboutScreenPreview() {
    val viewModal = MainViewModal()
    MediaLionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AboutScreen(viewModal = viewModal)
        }
    }
}


