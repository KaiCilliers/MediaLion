package com.sunrisekcdeveloper.medialion.newarch.components.collections.domain

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import com.github.michaelbull.result.Ok
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.CollectionRepositoryNew
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchAllCollectionsUseCaseTest {

    private lateinit var useCase: com.sunrisekcdeveloper.medialion.components.collections.domain.FetchAllCollectionsUseCaseNew
    private lateinit var collectionRepository: CollectionRepositoryNew.Fake

    @BeforeTest
    fun setup() {
        collectionRepository = CollectionRepositoryNew.Fake()
        useCase = com.sunrisekcdeveloper.medialion.components.collections.domain.FetchAllCollectionsUseCaseNew.Default(
            collectionRepository = collectionRepository
        )
    }

    @Test
    fun `when there is collections return them when invoked`() = runTest {
        val result = useCase()
        assertThat(result).isInstanceOf(Ok::class)
    }

    @Test
    fun `by default there should be a single collection called 'Favorite'`() = runTest {
        val (success, _) = useCase()

        assertThat(success).isNotNull()
        assertThat(success!!.collectionNames().map { it.value }).containsExactly(Title("Favorite").value)
    }

    @Test
    fun `when an error occurs with fetching collections return UnableToRetrieveCollections result`() = runTest {
        collectionRepository.forceFailure = true
        val (_, failure) = useCase()

        assertThat(failure).isNotNull()
    }

}