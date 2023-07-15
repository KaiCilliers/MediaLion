package com.sunrisekcdeveloper.medialion.newarch.components.collections.domain

import assertk.assertThat
import assertk.assertions.isGreaterThan
import assertk.assertions.isNotNull
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos.CollectionRepositoryNew
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class InsertDefaultCollectionsUseCaseTest {

    private lateinit var insertDefaultCollectionsUseCase: InsertDefaultCollectionsUseCase
    private lateinit var collectionRepository: CollectionRepositoryNew.Fake

    @BeforeTest
    fun setup() {
        collectionRepository = CollectionRepositoryNew.Fake()
        insertDefaultCollectionsUseCase = InsertDefaultCollectionsUseCase.Def(
            collectionRepository = collectionRepository
        )
    }

    @Test
    fun `return success when default collections have been successfully inserted`() = runTest {
        collectionRepository.clearCache()
        val allCollections = collectionRepository.all()

        assertTrue(allCollections.isEmpty())

        val (insertSuccess, _) = insertDefaultCollectionsUseCase()

        assertThat(insertSuccess).isNotNull()
        assertThat(insertSuccess).instanceOf(Ok::class)

        val currentCollections = collectionRepository.all()

        assertThat(currentCollections.size).isGreaterThan(0)
    }

    @Test
    fun `return failure when an exception occurs and default collections could not be inserted`() = runTest {
        collectionRepository.clearCache()
        val allCollections = collectionRepository.all()
        assertTrue(allCollections.isEmpty())

        collectionRepository.forceFailure = true
        val (_, insertFailure) = insertDefaultCollectionsUseCase()
        assertThat(insertFailure).isNotNull()
        assertThat(insertFailure).instanceOf(Err::class)

        collectionRepository.forceFailure = false
        val currentCollections = collectionRepository.all()

        assertTrue(currentCollections.isEmpty())
    }

}