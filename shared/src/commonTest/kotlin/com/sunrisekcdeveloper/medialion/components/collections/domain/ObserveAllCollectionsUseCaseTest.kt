package com.sunrisekcdeveloper.medialion.components.collections.domain

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import assertk.assertions.isNotNull
import com.github.michaelbull.result.Ok
import com.sunrisekcdeveloper.medialion.components.collections.domain.ObserveAllCollectionsUseCase
import com.sunrisekcdeveloper.medialion.components.collections.domain.UnableToObserveCollections
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.CollectionRepositoryNew
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
        val (collectionsFlow, _) = observeAllCollectionsUseCase()

        assertThat(collectionsFlow).isNotNull()
        assertThat(collectionsFlow).instanceOf(Ok::class)

        collectionsFlow!!.test {
            val titledMediaList = awaitItem()

            assertThat(titledMediaList.collectionNames().size).isGreaterThan(0)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `return a failure when an unexpected error occurs while observing collections`() = runTest {
        collectionRepository.forceFailure = true

        val (_, failure) = observeAllCollectionsUseCase()

        assertThat(failure).isNotNull()
        assertThat(failure).instanceOf(UnableToObserveCollections::class)
    }

    @Test
    fun `emit an updated list when list data changes`() = runTest {
        val (collectionsFlow, _) = observeAllCollectionsUseCase()

        assertThat(collectionsFlow).isNotNull()
        assertThat(collectionsFlow).instanceOf(Ok::class)

        collectionsFlow!!.test {

            var titledMediaList = awaitItem()
            assertThat(titledMediaList.collectionNames().size).isEqualTo(1)

            collectionRepository.upsert(CollectionNew.Def("New Collection"))

            titledMediaList = awaitItem()
            assertThat(titledMediaList.collectionNames().size).isEqualTo(2)

            cancelAndIgnoreRemainingEvents()
        }
    }

}