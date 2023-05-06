package com.sunrisekcdeveloper.medialion.android.ui.search

import com.sunrisekcdeveloper.medialion.domain.entities.Collection
import com.sunrisekcdeveloper.medialion.domain.search.CollectionComponent
import com.sunrisekcdeveloper.medialion.domain.search.CreateCollectionUseCase
import com.sunrisekcdeveloper.medialion.domain.search.FetchAllCollectionsUseCase
import com.sunrisekcdeveloper.medialion.domain.search.FetchCollectionUseCase
import com.sunrisekcdeveloper.medialion.domain.search.MLSearchViewModel
import com.sunrisekcdeveloper.medialion.domain.search.RemoveMediaFromCollectionUseCase
import com.sunrisekcdeveloper.medialion.domain.search.SaveMediaToCollectionUseCase
import com.sunrisekcdeveloper.medialion.domain.search.SearchAction
import com.sunrisekcdeveloper.medialion.domain.search.SearchComponent
import com.sunrisekcdeveloper.medialion.domain.search.SearchState
import com.sunrisekcdeveloper.medialion.domain.search.usecases.DocumentariesRelatedToUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.MovieDetailsUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.MoviesRelatedToUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.SuggestedMediaUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.TVDetailsUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.TVRelatedToUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.TopMediaResultsUseCase
import com.sunrisekcdeveloper.medialion.mappers.ListMapper
import com.sunrisekcdeveloper.medialion.mappers.Mapper
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
    private val sharedViewModel: MLSearchViewModel by lazy {
        with(backstack) {
            val searchComponent = SearchComponent.Default(
                movieDetails = MovieDetailsUseCase.Default(lookup()),
                tvDetails = TVDetailsUseCase.Default(lookup()),
                relatedDocumentariesUseCase = DocumentariesRelatedToUseCase.Default(lookup()),
                relatedMoviesUseCase = MoviesRelatedToUseCase.Default(lookup()),
                suggestedMediaUseCase = SuggestedMediaUseCase.Default(lookup()),
                topMediaResultsUseCase = TopMediaResultsUseCase.Default(
                    movieRepo = lookup(),
                    tvRepo = lookup(),
                    tvMapper = Mapper.TVShowEntity.DomainToMediaDomain(),
                    movieMapper = Mapper.MovieEntity.DomainToMediaDomain()
                ),
                tvRelatedToUseCase = TVRelatedToUseCase.Default(lookup()),
            )

            val collectionComponent = CollectionComponent.Default(
                saveMediaToCollection = SaveMediaToCollectionUseCase.Default(
                    collectionRepo = lookup(),
                    movieDetails = MovieDetailsUseCase.Default(lookup()),
                    tvDetails = TVDetailsUseCase.Default(lookup())
                ),
                removeMediaFromCollection = RemoveMediaFromCollectionUseCase.Default(lookup()),
                createCollection = CreateCollectionUseCase.Default(lookup()),
                fetchAllCollections = FetchAllCollectionsUseCase.Default(lookup()),
                fetchCollection = FetchCollectionUseCase.Default(lookup()),
            )

            return@lazy MLSearchViewModel(
                searchComponent = searchComponent,
                collectionComponent = collectionComponent,
                mediaItemMapper = Mapper.DomainToUI(),
                movieListMapper = ListMapper.Impl(Mapper.MovieEntity.DomainToUI()),
                coroutineScope = viewModelScope,
            )
        }
    }

    val state: StateFlow<SearchState> = sharedViewModel.state
    val collectionState: StateFlow<List<Collection>> = sharedViewModel.allCollectionsState

    fun submitAction(action: SearchAction) {
        sharedViewModel.submitAction(action)
    }
}