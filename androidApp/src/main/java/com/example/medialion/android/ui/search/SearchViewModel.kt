package com.example.medialion.android.ui.search

import com.example.medialion.domain.components.search.MLSearchViewModel
import com.example.medialion.domain.components.search.SearchAction
import com.example.medialion.domain.components.search.SearchComponent
import com.example.medialion.domain.components.search.SearchState
import com.example.medialion.domain.components.search.usecases.DocumentariesRelatedToUseCase
import com.example.medialion.domain.components.search.usecases.MovieDetailsUseCase
import com.example.medialion.domain.components.search.usecases.MoviesRelatedToUseCase
import com.example.medialion.domain.components.search.usecases.SuggestedMediaUseCase
import com.example.medialion.domain.components.search.usecases.TVRelatedToUseCase
import com.example.medialion.domain.components.search.usecases.TopMediaResultsUseCase
import com.example.medialion.domain.mappers.ListMapper
import com.example.medialion.domain.mappers.Mapper
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestackextensions.servicesktx.lookup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel(
    backstack: Backstack
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val sharedViewModel by lazy { MLSearchViewModel(
        searchComponent = SearchComponent.Default(
            movieDetails = MovieDetailsUseCase.Default(backstack.lookup()),
            relatedDocumentariesUseCase = DocumentariesRelatedToUseCase.Default(backstack.lookup()),
            relatedMoviesUseCase = MoviesRelatedToUseCase.Default(backstack.lookup()),
            suggestedMediaUseCase = SuggestedMediaUseCase.Default(backstack.lookup()),
            topMediaResultsUseCase = TopMediaResultsUseCase.Default(backstack.lookup()),
            tvRelatedToUseCase = TVRelatedToUseCase.Default(backstack.lookup())
        ),
        movieMapper = Mapper.MovieDomainToUi(),
        movieListMapper = ListMapper.Impl(Mapper.MovieDomainToUi()),
        coroutineScope = viewModelScope
    ) }

    val state: StateFlow<SearchState> = sharedViewModel.state

    fun submitAction(action: SearchAction) {
        sharedViewModel.submitAction(action)
    }
}