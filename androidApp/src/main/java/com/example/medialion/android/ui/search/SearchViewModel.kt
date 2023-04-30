package com.example.medialion.android.ui.search

import com.example.medialion.domain.components.search.MLSearchViewModel
import com.example.medialion.domain.components.search.SearchAction
import com.example.medialion.domain.components.search.SearchState
import com.example.medialion.domain.components.search.wip.MovieRemoteDataSource
import com.example.medialion.domain.components.search.wip.MovieRepository
import com.example.medialion.domain.components.search.wip.SuggestedMediaUseCase
import com.example.medialion.domain.mappers.ListMapper
import com.example.medialion.domain.mappers.Mapper
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestackextensions.servicesktx.lookup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class SearchViewModel(
    backstack: Backstack
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val sharedViewModel by lazy { MLSearchViewModel(
        searchMoviesByTitleUseCase = backstack.lookup(),
        relatedMoviesUseCase = backstack.lookup(),
        topRatedMoviesUseCase = backstack.lookup(),
        movieMapper = backstack.lookup(),
        client = backstack.lookup(),
        movieRemoteDataSource = MovieRemoteDataSource.MovieRemoteDataSourceTMDB(
            apiClient = backstack.lookup(),
            movieDetailMapper = Mapper.MovieDetailResponseToDomain(),
            movieMapper = Mapper.MovieResponseToDomain(),
            movieListMapper = ListMapper.Impl(Mapper.MovieResponseToDomain())
        ),
        suggestedMediaUseCase = SuggestedMediaUseCase.Default(
            movieRepo = MovieRepository.Default(
                remoteDataSource = MovieRemoteDataSource.MovieRemoteDataSourceTMDB(
                    apiClient = backstack.lookup(),
                    movieDetailMapper = Mapper.MovieDetailResponseToDomain(),
                    movieMapper = Mapper.MovieResponseToDomain(),
                    movieListMapper = ListMapper.Impl(Mapper.MovieResponseToDomain())
                )
            )
        ),
        coroutineScope = viewModelScope
    ) }

    val state: StateFlow<SearchState> = sharedViewModel.state

    fun submitAction(action: SearchAction) {
        sharedViewModel.submitAction(action)
    }
}