package com.sunrisekcdeveloper.medialion.features.mycollection

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.sunrisekcdeveloper.medialion.components.collections.domain.FetchAllCollectionsUseCaseNew
import com.sunrisekcdeveloper.medialion.features.mycollection.FailedToFetchCollections
import com.sunrisekcdeveloper.medialion.features.mycollection.FetchMyCollectionsMedia
import com.sunrisekcdeveloper.medialion.features.mycollection.Loading
import com.sunrisekcdeveloper.medialion.features.mycollection.MLMyCollectionViewModelNew
import com.sunrisekcdeveloper.medialion.features.mycollection.MyCollectionsContent
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
    private lateinit var fetchMyCollectionsUseCase: com.sunrisekcdeveloper.medialion.components.collections.domain.FetchAllCollectionsUseCaseNew.Fake

    private val scope = TestScope()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher(scope.testScheduler))
        fetchMyCollectionsUseCase = com.sunrisekcdeveloper.medialion.components.collections.domain.FetchAllCollectionsUseCaseNew.Fake()
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