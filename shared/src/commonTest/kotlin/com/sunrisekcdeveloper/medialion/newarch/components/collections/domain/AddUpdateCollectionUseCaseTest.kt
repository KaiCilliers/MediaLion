package com.sunrisekcdeveloper.medialion.newarch.components.collections.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.SingleMediaItem
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

    @Test
    fun `any updates to a collection's media content should be saved when collection already exists`() = runTest {
        val media = listOf(
            SingleMediaItem.Movie("Movie #1"),
            SingleMediaItem.Movie("Movie #2"),
            SingleMediaItem.Movie("Movie #3"),
        )

        val collection: CollectionNew = CollectionNew.Def(
            name = "Xmas movies",
            media = listOf(media.first())
        )
        useCase(collection)


        collection.add(media[1])
        collection.add(media[2])
        useCase(collection)

        val (savedCollection, _) = collectionRepository.collection(Title("Xmas movies"))

        assertThat(savedCollection).isNotNull()
        assertThat(savedCollection!!.media().size).isEqualTo(3)

        collection.remove(media[1])
        useCase(collection)

        val (savedCollection2, _) = collectionRepository.collection(Title("Xmas movies"))

        assertThat(savedCollection2).isNotNull()
        assertThat(savedCollection2!!.media().size).isEqualTo(2)
    }
}