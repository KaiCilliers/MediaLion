package com.example.medialion.android.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.medialion.android.R
import com.example.medialion.android.theme.MediaLionTheme
import com.example.medialion.android.ui.search.ui.SearchScreen
import com.example.medialion.domain.components.search.SearchAction
import com.example.medialion.domain.components.search.SearchState
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import kotlinx.coroutines.flow.onEach

class SearchFragment : Fragment() {

    private val viewModel by lazy { lookup<SearchViewModel>() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MediaLionTheme {
                   Surface(
                       modifier = Modifier.fillMaxSize(),
                       color = MaterialTheme.colors.background
                   ){
                        val state by viewModel.state.collectAsState()
                       println("deadpool -  FRAGMENT i got a state $state")

                       SearchScreen(state = state, submitAction = { viewModel.submitAction(it)} )
                   }
                }
            }
        }
    }
}

@Composable
fun InitialSearch (){
    Column {
        val textState = remember { mutableStateOf(TextFieldValue("")) }
Row () {
    Box {
        Image(
            painter = painterResource(id = R.drawable.back_arrow_icon),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 12.dp, top = 12.dp, bottom = 12.dp)
                .size(24.dp))
    }

    Spacer(modifier = Modifier.weight(1F))

    Box {
       Image(
           painter = painterResource(id = R.drawable.about_icon),
           contentDescription = "",
           modifier = Modifier
               .align(Alignment.TopEnd)
               .padding(end = 12.dp, top = 12.dp, bottom = 12.dp)
               .size(24.dp))
    }
}
SearchView(
    state = textState,
    label = com.example.medialion.R.string.empty_search,
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text
    )
)
        Spacer(modifier = Modifier.height(20.dp))

   Text(
       text = stringResource(id = com.example.medialion.R.string.top_suggestions),
       color = Color.White,
       modifier = Modifier.padding(start = 12.dp),
       style = MaterialTheme.typography.h2)

        Spacer(modifier = Modifier.height(12.dp))

   TopSuggestionsList()
    }
}

@Composable
fun SearchView(
    state: MutableState<TextFieldValue>,
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
){
    TextField(
        value = state.value, onValueChange = {value ->
      state.value = value
    },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(
            text = stringResource(id = label),
            color = Color.White,
            style = MaterialTheme.typography.h1
        )},
        singleLine = true,
        keyboardOptions = keyboardOptions,
        shape = RectangleShape,
        leadingIcon = {
         Image(
             painter = painterResource(id = R.drawable.search_icon),
             contentDescription = "",
             modifier = Modifier
                 .padding(15.dp)
                 .size(28.dp))
        }
        )
}

@Composable
fun TopSuggestionsList() {
    val list = mutableListOf<MovieItem>()
    var currentStep by remember { mutableStateOf(1) }
    var favorited by remember { mutableStateOf(2) }
    (1..100).forEach { movieNumber ->
        list.add(
            MovieItem(
            image =  R.drawable.column_place_holder,
            title = stringResource(id = com.example.medialion.R.string.title),
            favorited = false
        )
        )
    }
    LazyColumn{
        items(list) { singleMovie ->
            when (currentStep) {
                1 -> {
                    TopSuggestionListItem(
                        image = R.drawable.heart_outline_icon,
                        movieItem = singleMovie,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        onClick = { movieTitle ->
                            println(movieTitle)
                        }
                    )
                }
                2 -> {
                    TopSuggestionListItem(
                        movieItem = singleMovie,
                        image = R.drawable.heart_filled_icon,
                        onClick = { currentStep = 1 })

                }
            }

        }
    }
}

@Composable
fun TopSuggestionListItem(
    movieItem: MovieItem,
    modifier: Modifier = Modifier,
    image: Int,
    onClick: (String) -> Unit

){
Row (
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .sizeIn(minHeight = 100.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    Box (
        modifier = Modifier
            .size(width = 180.dp, height = 100.dp)
    ) {
        Image(
            painter = painterResource(id = movieItem.image),
            contentDescription = "",
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.FillWidth)
    }


    Text(
        text = movieItem.title,
        color = Color.White,
        modifier = Modifier
            .padding(start = 12.dp),
        style = MaterialTheme.typography.h3)

    Spacer(modifier = Modifier.weight(1F))

    Image(
        painter = painterResource(id = image),
        contentDescription = "",
        modifier = Modifier
            .clickable { onClick(movieItem.title) }
            .size(24.dp)
    )
}
}

data class MovieItem(
    val image: Int,
    val title: String,
    val favorited: Boolean
)


@Preview
@Composable
fun MediaLionPreview() {
    MediaLionTheme {
        InitialSearch()
    }

}
