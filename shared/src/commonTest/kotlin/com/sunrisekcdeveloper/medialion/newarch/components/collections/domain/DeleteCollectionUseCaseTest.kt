package com.sunrisekcdeveloper.medialion.newarch.components.collections.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.github.michaelbull.result.Ok
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.CollectionRepositoryNew
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteCollectionUseCaseTest {

    private lateinit var deleteCollectionUseCase: com.sunrisekcdeveloper.medialion.components.collections.domain.DeleteCollectionUseCaseNew
    private lateinit var collectionRepository: CollectionRepositoryNew.Fake

    @BeforeTest
    fun setup() {
        collectionRepository = CollectionRepositoryNew.Fake()
        deleteCollectionUseCase = com.sunrisekcdeveloper.medialion.components.collections.domain.DeleteCollectionUseCaseNew.Def(
            collectionRepository = collectionRepository
        )
    }

    @Test
    fun `return success when a collection was successfully deleted`() = runTest {
        val newCollection = CollectionNew.Def("Holiday Specials")
        collectionRepository.upsert(newCollection)
        val (success, _) = deleteCollectionUseCase(newCollection)

        assertThat(success).instanceOf(Ok::class)
        assertTrue(collectionRepository.collection(Title("Holiday Specials")).isEmpty())
    }

    @Test
    fun `return failure when there is no matching collection to delete`() = runTest {
        val newCollection = CollectionNew.Def("Holiday Specials")
        val (_, failure) = deleteCollectionUseCase(newCollection)

        assertThat(failure).instanceOf(com.sunrisekcdeveloper.medialion.components.collections.domain.CollectionDoesNotExist::class)
        assertTrue(collectionRepository.collection(Title("Holiday Specials")).isEmpty())
    }

    @Test
    fun `return failure when an unexpected error occurs when trying to delete a collection`() = runTest {
        val newCollection = CollectionNew.Def("Holiday Specials")
        collectionRepository.upsert(newCollection)

        collectionRepository.forceFailure = true
        val (success, failure) = deleteCollectionUseCase(newCollection)

        assertThat(success).isNull()
        assertThat(failure).instanceOf(com.sunrisekcdeveloper.medialion.components.collections.domain.FailedToDeleteCollection::class)
        assertThat(collectionRepository.collection(Title("Holiday Specials")).size).isEqualTo(1)
    }
}