package com.example.medialion.android.ui.search

import com.example.medialion.domain.components.saveToCollection.CollectionComponent
import com.example.medialion.domain.components.search.MLSearchViewModel
import com.example.medialion.domain.components.search.SearchAction
import com.example.medialion.domain.components.search.SearchComponent
import com.example.medialion.domain.components.search.SearchState
import com.example.medialion.domain.components.search.usecases.DocumentariesRelatedToUseCase
import com.example.medialion.domain.components.search.usecases.MovieDetailsUseCase
import com.example.medialion.domain.components.search.usecases.MoviesRelatedToUseCase
import com.example.medialion.domain.components.search.usecases.SuggestedMediaUseCase
import com.example.medialion.domain.components.search.usecases.TVDetailsUseCase
import com.example.medialion.domain.components.search.usecases.TVRelatedToUseCase
import com.example.medialion.domain.components.search.usecases.TopMediaResultsUseCase
import com.example.medialion.domain.mappers.ListMapper
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.local.DatabaseDriverFactory
import com.example.medialion.local.MediaLionDatabaseFactory
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
            tvDetails = TVDetailsUseCase.Default(backstack.lookup()),
            relatedDocumentariesUseCase = DocumentariesRelatedToUseCase.Default(backstack.lookup()),
            relatedMoviesUseCase = MoviesRelatedToUseCase.Default(backstack.lookup()),
            suggestedMediaUseCase = SuggestedMediaUseCase.Default(backstack.lookup()),
            topMediaResultsUseCase = TopMediaResultsUseCase.Default(
                movieRepo = backstack.lookup(),
                tvRepo = backstack.lookup(),
                tvMapper = Mapper.TVDomainToMediaDomain(),
                movieMapper = Mapper.MovieDomainToMediaDomain(),
            ),
            tvRelatedToUseCase = TVRelatedToUseCase.Default(backstack.lookup())
        ),
        myCollectionComponent = CollectionComponent.Default(
            database = MediaLionDatabaseFactory(DatabaseDriverFactory(backstack.lookup())).create(),
            movieRemoteDataSource = backstack.lookup(),
            tvRemoteDataSource = backstack.lookup(),
            movieEntityMapper = Mapper.MovieDetailDomainToEntity(),
            tvEntityMapper = Mapper.TVShowDetailDomainToTVShowEntity(),
        ),
        movieListMapper = ListMapper.Impl(Mapper.MovieDomainToMediaUI()),
        mediaItemMapper = Mapper.MediaDomainToMediaUI(),
        coroutineScope = viewModelScope
    ) }

    val state: StateFlow<SearchState> = sharedViewModel.state
    val collectionState: StateFlow<List<Pair<String, List<Int>>>> = sharedViewModel.allCollectionsState

    fun submitAction(action: SearchAction) {
        sharedViewModel.submitAction(action)
    }
}