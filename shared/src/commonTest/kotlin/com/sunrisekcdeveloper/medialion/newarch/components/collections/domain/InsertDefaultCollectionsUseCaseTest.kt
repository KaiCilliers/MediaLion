package com.sunrisekcdeveloper.medialion.newarch.components.collections.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import assertk.assertions.isNotNull
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos.CollectionRepositoryNew
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

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
        val (success, _) = collectionRepository.all()

        assertThat(success).isNotNull()
        assertThat(success!!.size).isEqualTo(0)

        val (insertSuccess, _) = insertDefaultCollectionsUseCase()

        assertThat(insertSuccess).isNotNull()

        val (currentCollections, _) = collectionRepository.all()

        assertThat(currentCollections).isNotNull()
        assertThat(currentCollections!!.size).isGreaterThan(0)
    }

    @Test
    fun `return failure when an exception occurs and default collections could not be inserted`() = runTest {
        collectionRepository.clearCache()

        val (success, _) = collectionRepository.all()

        assertThat(success).isNotNull()
        assertThat(success!!.size).isEqualTo(0)

        collectionRepository.forceFailure = true
        val (_, insertFailure) = insertDefaultCollectionsUseCase()

        assertThat(insertFailure).isNotNull()

        collectionRepository.forceFailure = false
        val (currentCollections, _) = collectionRepository.all()

        assertThat(currentCollections).isNotNull()
        assertThat(currentCollections!!.size).isEqualTo(0)
    }

}