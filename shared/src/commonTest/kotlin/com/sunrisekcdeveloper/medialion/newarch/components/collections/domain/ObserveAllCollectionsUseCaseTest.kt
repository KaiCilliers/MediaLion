package com.sunrisekcdeveloper.medialion.newarch.components.collections.domain

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import assertk.assertions.isNotNull
import com.github.michaelbull.result.Ok
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos.CollectionRepositoryNew
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ObserveAllCollectionsUseCaseTest {

    private lateinit var observeAllCollectionsUseCase: ObserveAllCollectionsUseCase
    private lateinit var collectionRepository: CollectionRepositoryNew.Fake

    @BeforeTest
    fun setup() {
        collectionRepository = CollectionRepositoryNew.Fake()
        observeAllCollectionsUseCase = ObserveAllCollectionsUseCase.Def(
            collectionRepository = collectionRepository
        )
    }

    @Test
    fun `return all collections when invoked`() = runTest {
        observeAllCollectionsUseCase().test {
            val (success,_) = awaitItem()

            assertThat(success).isNotNull()
            assertThat(success).instanceOf(Ok::class)
            assertThat(success!!.collectionNames().size).isGreaterThan(0)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `return a failure when an unexpected error occurs while observing collections`() = runTest {
        collectionRepository.forceFailure = true
        observeAllCollectionsUseCase().test {
            val (_, failure) = awaitItem()

            assertThat(failure).isNotNull()
            assertThat(failure).instanceOf(UnableToObserveCollections::class)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emit an updated list when list data changes`() = runTest {
        observeAllCollectionsUseCase().test {
            val (success,_) = awaitItem()

            assertThat(success).isNotNull()
            assertThat(success).instanceOf(Ok::class)
            assertThat(success!!.collectionNames().size).isEqualTo(1)

            collectionRepository.upsert(CollectionNew.Def("New Collection"))

            val (success2,_) = awaitItem()

            assertThat(success2).isNotNull()
            assertThat(success2).instanceOf(Ok::class)
            assertThat(success2!!.collectionNames().size).isEqualTo(2)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emit a failure when an error occurs after an initial successful collections emission`() = runTest {
        observeAllCollectionsUseCase().test {
            val (success,_) = awaitItem()

            assertThat(success).isNotNull()
            assertThat(success).instanceOf(Ok::class)
            assertThat(success!!.collectionNames().size).isGreaterThan(0)

            collectionRepository.forceFailure = true

            val (_, failure) = awaitItem()
            assertThat(failure).isNotNull()
            assertThat(failure).instanceOf(UnableToObserveCollections::class)

            cancelAndIgnoreRemainingEvents()
        }
    }

}