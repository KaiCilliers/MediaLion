package com.example.medialion.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.medialion.DiscoveryComponent
import com.example.medialion.Result
import com.example.medialion.SharedRes
import com.example.medialion.SharedTextResource
import com.example.medialion.android.theme.LocalSpacing
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.android.theme.spacing
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {
    val discoveryComponent by lazy { lookup<DiscoveryComponent>() }
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {

                val scope = rememberCoroutineScope()
                var sliderValue by remember {
                    mutableStateOf(0f)
                }
                var mediaList by remember { mutableStateOf(listOf<Result>()) }
                LaunchedEffect(true) {
                    scope.launch { 
                        mediaList = discoveryComponent.allLaunches()
                    }
                }

                MediaLionTheme {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorResource(id = SharedRes.colors.valueColor.resourceId))
                    ) {
                        Column(modifier = Modifier.clickable { backstack.goBack() }){
                            LazyVerticalGrid(columns = GridCells.Fixed(2)){
                                items(mediaList) {
                                    Card(
                                        modifier = Modifier.border(
                                            width = MaterialTheme.spacing.small,
                                            brush = Brush.sweepGradient(listOf(
                                                MaterialTheme.colors.primary,
                                                MaterialTheme.colors.error
                                            )),
                                            shape = RectangleShape
                                        ),
                                    ) {
                                        Column {
                                            AsyncImage(
                                                model = ImageRequest.Builder(LocalContext.current)
                                                    .data(DiscoveryComponent.baseUrl + it.posterPath)
                                                    .crossfade(true)
                                                    .build(),
                                                contentDescription = null,
                                                placeholder = painterResource(id = R.drawable.placeholder_image),
                                                contentScale = ContentScale.Crop,
                                                error = painterResource(id = R.drawable.placeholder_image),
                                                modifier = Modifier.clip(CircleShape)
                                            )
                                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                                            Text(text = it.title ?: it.originalTitle ?: it.name ?: "placeholder title")
                                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                                            SubcomposeAsyncImage(
                                                model = ImageRequest.Builder(LocalContext.current)
                                                    .data(DiscoveryComponent.baseUrl + it.posterPath)
                                                    .crossfade(true)
                                                    .build(),
                                                loading = { CircularProgressIndicator() },
                                                contentDescription = null
                                            )
                                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                                            SubcomposeAsyncImage(
                                                model = ImageRequest.Builder(LocalContext.current)
                                                    .data(DiscoveryComponent.baseUrl + it.posterPath)
                                                    .crossfade(true)
                                                    .build(),
                                                contentDescription = null,
                                            ) {
                                                val state = painter.state
                                                if (state is AsyncImagePainter.State.Loading ) {
                                                    CircularProgressIndicator()
                                                } else if(state is AsyncImagePainter.State.Error) {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.placeholder_image),
                                                        contentDescription = null
                                                    )
                                                } else {
                                                    SubcomposeAsyncImageContent()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
