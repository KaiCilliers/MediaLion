package com.sunrisekcdeveloper.medialion.features.discovery

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import com.github.michaelbull.result.Err
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchDiscoveryContentUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchMediaWithCategoryUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.DiscoveryPage
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import io.ktor.util.reflect.instanceOf
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
class MLDiscoveryViewModelNewTest {

    private lateinit var sut: MLDiscoveryViewModelNew
    private lateinit var fetchDiscoveryContentUseCase: FetchDiscoveryContentUseCase.Fake
    private lateinit var fetchMediaForCategoryUseCase: FetchMediaWithCategoryUseCase.Fake

    private val scope = TestScope()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher(scope.testScheduler))
        fetchDiscoveryContentUseCase = FetchDiscoveryContentUseCase.Fake()
        fetchMediaForCategoryUseCase = FetchMediaWithCategoryUseCase.Fake()
        sut = MLDiscoveryViewModelNew.D(
            fetchDiscoveryContentUseCase = fetchDiscoveryContentUseCase,
            fetchMediaForCategoryUseCase = fetchMediaForCategoryUseCase,
            coroutineScope = scope,
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading when observing state`() = runTest {
        sut.state.test {
            assertThat(awaitItem()).instanceOf(Loading::class)
        }
    }

    @Test
    fun `return a list of titled media when submitting a discovery page`() = runTest {
        sut.state.test {
            awaitItem()
            sut.submit(FetchPageMediaContent(DiscoveryPage.Movies))

            val result = awaitItem()
            assertThat(result).instanceOf(Content::class)
            assertThat((result as Content).media.collectionNames().size).isGreaterThan(0)
        }
    }

    @Test
    fun `return an error when an exception occurs while fetching discovery page content`() = runTest {
        fetchDiscoveryContentUseCase.forceFailure = true
        sut.state.test {
            awaitItem()
            sut.submit(FetchPageMediaContent(DiscoveryPage.Movies))

            val result = awaitItem()
            assertThat(result).instanceOf(Err::class)
        }
    }

    @Test
    fun `return a single titled media when submitting a media category`() = runTest {
        sut.state.test {
            awaitItem()
            sut.submit(FetchMediaForCategory(MediaCategory.D("fantasy")))

            val result = awaitItem()
            assertThat(result).instanceOf(Content::class)
            assertThat((result as Content).media.collectionNames().size).isEqualTo(1)
        }
    }

    @Test
    fun `return an error when an exception occurs while fetching media category content`() = runTest {
        fetchMediaForCategoryUseCase.forceFailure = true
        sut.state.test {
            awaitItem()
            sut.submit(FetchMediaForCategory(MediaCategory.D("fantasy")))

            val result = awaitItem()
            assertThat(result).instanceOf(Err::class)
        }
    }

}