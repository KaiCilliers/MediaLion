package com.sunrisekcdeveloper.medialion.newarch.mycollection

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.sunrisekcdeveloper.medialion.newarch.mycollection.usecase.FetchMyCollectionsUseCaseNew
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
class MLMyCollectionViewModelTest {


    private lateinit var sut: MLMyCollectionViewModelNew
    private lateinit var fetchMyCollectionsUseCase: FetchMyCollectionsUseCaseNew.Fake

    private val scope = TestScope()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher(scope.testScheduler))
        fetchMyCollectionsUseCase = FetchMyCollectionsUseCaseNew.Fake()
        sut = MLMyCollectionViewModelNew.Default(
            fetchMyCollectionsUseCase = fetchMyCollectionsUseCase,
            coroutineScope = scope,
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when instantiated start at the loading state while fetching content`() = runTest {
        sut.state.test {
            assertThat(awaitItem()).isInstanceOf(Loading::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when collection content is available change state to display the collection content`() = runTest {
        sut.state.test {
            assertThat(awaitItem()).isInstanceOf(Loading::class)

            sut.submit(FetchMyCollectionsMedia)

            assertThat(awaitItem()).isInstanceOf(MyCollectionsContent::class)
        }
    }

    @Test
    fun `when there is some problem fetching collection content show an error state`() = runTest {
        sut.state.test {
            assertThat(awaitItem()).isInstanceOf(Loading::class)

            fetchMyCollectionsUseCase.hinder = true
            sut.submit(FetchMyCollectionsMedia)

            assertThat(awaitItem()).isInstanceOf(FailedToFetchCollections::class)
        }
    }
}