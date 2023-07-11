package com.sunrisekcdeveloper.medialion.newarch.components.collections.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos.CollectionRepositoryNew
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddUpdateCollectionUseCaseTest {

    private lateinit var useCase: AddUpdateCollectionUseCase
    private lateinit var collectionRepository: CollectionRepositoryNew.Fake

    @BeforeTest
    fun setup() {
        collectionRepository = CollectionRepositoryNew.Fake()
        useCase = AddUpdateCollectionUseCase.Def(
            collectionRepository = collectionRepository
        )
    }

    @Test
    fun `return success if new collection was added successfully`() = runTest {
        val collection = CollectionNew.Def("Weekend Chill")
        useCase(collection)

        val (savedCollection, _) = collectionRepository.collection(Title("Weekend Chill"))

        assertThat(savedCollection).isNotNull()
        assertThat(savedCollection!!).isEqualTo(collection)
    }

    @Test
    fun `return an error if the collection was not able to be added`() = runTest {
        val collection = CollectionNew.Def("Weekend Chill")
        collectionRepository.forceFailure = true

        val (_, failure) = useCase(collection)

        assertThat(failure).isNotNull()
    }

    @Test
    fun `if collection exists already update the existing collection`() = runTest {
        var collection: CollectionNew = CollectionNew.Def("Weekend Chill")
        useCase(collection)

        collection = collection.rename(Title("Weekend Party"))
        useCase(collection)

        val (savedCollection, _) = collectionRepository.collection(Title("Weekend Party"))

        assertThat(savedCollection).isNotNull()
        assertThat(savedCollection).isEqualTo(collection)
    }
}