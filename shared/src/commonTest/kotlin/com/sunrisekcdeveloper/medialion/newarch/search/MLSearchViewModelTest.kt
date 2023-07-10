package com.sunrisekcdeveloper.medialion.newarch.search

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.sunrisekcdeveloper.medialion.newarch.search.MLSearchViewModelNew
import com.sunrisekcdeveloper.medialion.newarch.search.SearchScreenAction
import com.sunrisekcdeveloper.medialion.newarch.search.SearchUIState
import com.sunrisekcdeveloper.medialion.newarch.search.factories.SearchQueryFactory
import com.sunrisekcdeveloper.medialion.newarch.search.usecase.SearchForMediaUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MLSearchViewModelTest {

    private lateinit var sut: MLSearchViewModelNew
    private lateinit var searchForMediaUseCase: SearchForMediaUseCase.Fake

    private val scope = TestScope()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher(scope.testScheduler))
        searchForMediaUseCase = SearchForMediaUseCase.Fake()
        sut = MLSearchViewModelNew.Default(
            searchForMediaUseCase = searchForMediaUseCase,
            coroutineScope = scope
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when instantiated show top suggestions state`() = runTest {
        sut.state.test {
            assertThat(awaitItem()).isInstanceOf(SearchUIState.TopSuggestions::class)
        }
    }

    @Test
    fun `when a search query is submitted and it can be executed show loading state`() = runTest {
        sut.state.test {

            awaitItem() // initial state

            sut.submitAction(
                SearchScreenAction.SubmitSearchQuery(
                    SearchQueryFactory()
                        .asExecutable()
                        .produce()
                )
            )

            assertThat(awaitItem()).isInstanceOf(SearchUIState.Loading::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when search query is submitted and it is not ready to be executed do not update state`() = runTest {
        sut.state.test {

            sut.submitAction(
                SearchScreenAction.SubmitSearchQuery(
                SearchQueryFactory()
                    .asNotExecutable()
                    .produce()
            ))

            advanceTimeBy(400)
            assertThat(expectMostRecentItem()).isInstanceOf(SearchUIState.TopSuggestions::class)
        }
    }

    @Test
    fun `when search results are available display the results state`() = runTest {
        sut.state.test {

            awaitItem()

            sut.submitAction(
                SearchScreenAction.SubmitSearchQuery(
                SearchQueryFactory()
                    .asExecutable()
                    .produce()
            ))

            assertThat(awaitItem()).isInstanceOf(SearchUIState.Loading::class)
            assertThat(awaitItem()).isInstanceOf(SearchUIState.Results::class)
        }
    }

    @Test
    fun `when there is no search results to display show an empty state`() = runTest {
        sut.state.test {

            awaitItem()
            searchForMediaUseCase.returnEmptyResults = true

            sut.submitAction(
                SearchScreenAction.SubmitSearchQuery(
                SearchQueryFactory()
                    .asExecutable()
                    .produce()
            ))

            assertThat(awaitItem()).isInstanceOf(SearchUIState.Loading::class)
            assertThat(awaitItem()).isInstanceOf(SearchUIState.NoResults::class)

        }
    }
}

