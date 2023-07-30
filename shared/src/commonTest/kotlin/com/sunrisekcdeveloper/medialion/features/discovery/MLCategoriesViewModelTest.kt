package com.sunrisekcdeveloper.medialion.features.discovery

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isGreaterThan
import assertk.assertions.isTrue
import com.sunrisekcdeveloper.medialion.components.shared.domain.FetchAllMediaCategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MLCategoriesViewModelTest {

    private lateinit var sut: MLCategoriesViewModel
    private lateinit var fetchAllMediaCategoriesUseCase: FetchAllMediaCategoriesUseCase.Fake

    private val scope = TestScope()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher(scope.testScheduler))
        fetchAllMediaCategoriesUseCase = FetchAllMediaCategoriesUseCase.Fake()
        sut = MLCategoriesViewModel.D(
            fetchAllMediaCategoriesUseCase = fetchAllMediaCategoriesUseCase,
            coroutineScope = scope,
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading when observing state`() = runTest {
        sut.catState.test {
            assertThat(awaitItem() is CategoriesUIState.Loading).isTrue()
        }
    }

    @Test
    fun `return a list of categories after submitting an action to fetch them`() = runTest {
        sut.catState.test {
            awaitItem()

            sut.submit(FetchAllCategories)

            val result = awaitItem()
            assertThat(result is CategoriesUIState.Content).isTrue()
            assertThat((result as CategoriesUIState.Content).categories.size).isGreaterThan(0)
        }
    }

    @Test
    fun `return an error if an exception occurs while fetching the categories`() = runTest {
        fetchAllMediaCategoriesUseCase.forceFailure = true
        sut.catState.test {
            awaitItem()

            sut.submit(FetchAllCategories)

            val result = awaitItem()
            assertThat(result is CategoriesUIState.Error).isTrue()
        }
    }

}