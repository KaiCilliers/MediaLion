package com.example.medialion.domain.components.search

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import com.example.medialion.domain.mappers.ListMapper
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieUiModel
import com.example.medialion.flow.CStateFlow
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MLSearchViewModelTest {

    private lateinit var sut: MLSearchViewModel
    private lateinit var searchUseCase: SearchMoviesUseCase.Fake
    private lateinit var relatedUseCase: RelatedMoviesUseCase.Fake
    private lateinit var topRatedUseCase: TopRatedMoviesUseCase.Fake
    private lateinit var movieMapper: ListMapper<Movie, MovieUiModel>

    @BeforeTest
    fun setup() {
        searchUseCase = SearchMoviesUseCase.Fake()
        relatedUseCase = RelatedMoviesUseCase.Fake()
        topRatedUseCase = TopRatedMoviesUseCase.Fake()
        movieMapper = ListMapper.Impl(Mapper.MovieUiMapper())
        sut = MLSearchViewModel(
            searchMoviesByTitleUseCase = searchUseCase,
            relatedMoviesUseCase = relatedUseCase,
            topRatedMoviesUseCase = topRatedUseCase,
            movieMapper = movieMapper,
            coroutineScope = CoroutineScope(UnconfinedTestDispatcher())
        )
    }

    @Test
    fun `initial state is idle`() = runTest {
        sut.state.asStateFlow.test {
            val state = awaitItem()
            delay(100_000)
            assertThat(state).instanceOf(SearchState.Idle::class)
        }
    }

    @Test
    fun `submitting a search query should trigger loading state`() = runTest {
        sut.state.asStateFlow.test {
            sut.submitAction(SearchAction.SubmitSearchQuery("movie title"))
            val state = awaitItem()
            assertThat(state).instanceOf(SearchState.Loading::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `submitting a search query should result in a result state after loading state`() = runTest {
        sut.state.asStateFlow.test {
            sut.submitAction(SearchAction.SubmitSearchQuery("movie title"))
            delay(1_000)
            val state = expectMostRecentItem()
            assertThat(state).instanceOf(SearchState.Results::class)
        }
    }

    @Test
    fun `submitting add to favorites action should return an idle state with updated suggested movies`() = runTest {
        sut.state.asStateFlow.test {
            delay(5_000)
            expectMostRecentItem()

            val movieIdToUpdate = topRatedUseCase.movies.first().id
            sut.submitAction(SearchAction.AddToFavorites(movieIdToUpdate))

            val state = awaitItem()
            assertThat(state).isInstanceOf(SearchState.Idle::class)

            val idleState = state as SearchState.Idle
            assertThat(idleState.suggestedMedia.find { it.id == movieIdToUpdate }).isNotNull()
            assertThat(idleState.suggestedMedia.find { it.id == movieIdToUpdate }?.isFavorited).isEqualTo(true)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `submitting remove from favorites action should return an idle state with the updated suggested movies`() = runTest {
        sut.state.asStateFlow.test {
            delay(5_000)
            expectMostRecentItem()

            val movieIdToUpdate = topRatedUseCase.movies.first().id
            sut.submitAction(SearchAction.AddToFavorites(movieIdToUpdate))
            awaitItem()

            sut.submitAction(SearchAction.RemoveFromFavorites(movieIdToUpdate))
            val state = awaitItem()
            assertThat(state).instanceOf(SearchState.Idle::class)

            val idleState = state as SearchState.Idle
            assertThat(idleState.suggestedMedia.find { it.id == movieIdToUpdate }?.isFavorited).isEqualTo(false)
        }
    }

    // todo clear action and error states

}

val <T: Any>CStateFlow<T>.asStateFlow: StateFlow<T>
    get() = this as StateFlow<T>