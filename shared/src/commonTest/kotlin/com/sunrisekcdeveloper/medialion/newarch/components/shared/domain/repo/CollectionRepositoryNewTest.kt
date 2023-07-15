package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repo

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import assertk.assertions.isNotNull
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.oldArch.mappers.Mapper
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.collection.CollectionEntityDto
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.collection.CollectionLocalDataSource
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos.CollectionRepositoryNew
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class CollectionRepositoryNewTest {

    private lateinit var collectionRepository: CollectionRepositoryNew
    private lateinit var collectionLocalDataSource: CollectionLocalDataSource.Fake

    private val entityMapper = object : Mapper<CollectionEntityDto, CollectionNew> {
        override fun map(input: CollectionEntityDto): CollectionNew {
            return CollectionNew.Def(
                id = input.id,
                title = input.title,
                media = input.media
            )
        }
    }

    private val domainMapper = object : Mapper<CollectionNew, CollectionEntityDto> {
        override fun map(input: CollectionNew): CollectionEntityDto {
            return CollectionEntityDto(
                id = input.identifier(),
                title = input.title(),
                media = input.media()
            )
        }
    }

    @BeforeTest
    fun setup() {
        collectionLocalDataSource = CollectionLocalDataSource.Fake()
        collectionRepository = CollectionRepositoryNew.D(
            localDataSource = collectionLocalDataSource,
            entityMapper = entityMapper,
            domainMapper = domainMapper
        )
    }

    @Test
    fun `return a flow of collections when observed`() = runTest {
        val collectionsFlow = collectionRepository.observe()
        assertThat(collectionsFlow.first()).isNotNull()
    }

    @Test
    fun `throw exception when a failure occurs while observing collections`() = runTest {
        collectionLocalDataSource.forceFailure = true
        assertFailsWith<Exception> { collectionRepository.observe() }
    }

    @Test
    fun `return a list of collections when all function is invoked`() = runTest {
        collectionRepository.upsert(CollectionNew.Def("name"))

        val collections = collectionRepository.all()
        assertThat(collections.size).isGreaterThan(0)
    }

    @Test
    fun `throw exception when a failure occurs while retrieving all collections`() = runTest {
        collectionLocalDataSource.forceFailure = true
        assertFailsWith<Exception> { collectionRepository.all() }
    }

    @Test
    fun `return a single collection when a collection title is received that matches an existing collection`() = runTest {
        collectionRepository.upsert(CollectionNew.Def("name"))

        val collection = collectionRepository.collection(Title("name"))
        assertThat(collection.size).isEqualTo(1)
    }

    @Test
    fun `return an empty collection when no collection is found with matching title`() = runTest {
        collectionRepository.upsert(CollectionNew.Def("name"))

        val collection = collectionRepository.collection(Title("does not exist"))
        assertThat(collection.size).isEqualTo(0)
    }

    @Test
    fun `throw an exception when a failure occurs while looking for a collection`() = runTest {
        collectionRepository.upsert(CollectionNew.Def("name"))
        collectionLocalDataSource.forceFailure = true

        assertFailsWith<Exception> { collectionRepository.collection(Title("name")) }
    }

    @Test
    fun `add a new collection when upsert is called`() = runTest {
        val newCollection = CollectionNew.Def(
            name = "My Collection",
            media = listOf(SingleMediaItem.Movie("movie #1"))
        )
        collectionRepository.upsert(newCollection)

        val collections = collectionRepository.all()
        assertThat(collections.size).isEqualTo(1)
        assertThat(collections.first().title()).isEqualTo(newCollection.title())
        assertThat(collections.first().media().size).isEqualTo(newCollection.media().size)
    }

    @Test
    fun `update an existing collection title when upsert function is invoked with an existing collection with a new title`() = runTest {
        var newCollection: CollectionNew = CollectionNew.Def(
            name = "My Collection",
            media = listOf(SingleMediaItem.Movie("movie #1"))
        )
        collectionRepository.upsert(newCollection)

        newCollection = newCollection.rename(Title("new name"))
        collectionRepository.upsert(newCollection)


        val collections = collectionRepository.all()
        assertThat(collections.size).isEqualTo(1)
        assertThat(collections.first().title()).isEqualTo(Title("new name"))
    }

    @Test
    fun `update an existing collection content when upsert function is invoked with an existing collection with a updated content`() = runTest {
        val newCollection: CollectionNew = CollectionNew.Def(
            name = "My Collection",
            media = listOf(SingleMediaItem.Movie("movie #1"))
        )
        collectionRepository.upsert(newCollection)

        newCollection.add(SingleMediaItem.TVShow("tv show #1"))
        collectionRepository.upsert(newCollection)


        val collections = collectionRepository.all()
        assertThat(collections.size).isEqualTo(1)
        assertThat(collections.first().media().size).isEqualTo(2)
    }

    @Test
    fun `throw an exception when a failure occurs when trying to insert or update a collection`() = runTest {
        collectionLocalDataSource.forceFailure = true
        assertFailsWith<Exception> {
            val newCollection: CollectionNew = CollectionNew.Def(
                name = "My Collection",
                media = listOf(SingleMediaItem.Movie("movie #1"))
            )
            collectionRepository.upsert(newCollection)
        }
    }

    @Test
    fun `remove collection when delete function is called`() = runTest {
        val newCollection: CollectionNew = CollectionNew.Def(
            name = "My Collection",
            media = listOf(SingleMediaItem.Movie("movie #1"))
        )
        val newCollection2: CollectionNew = CollectionNew.Def(
            name = "My Collection #2",
            media = listOf(SingleMediaItem.Movie("movie #1"))
        )

        collectionRepository.run {
            upsert(newCollection)
            upsert(newCollection2)
            delete(newCollection)
        }

        val allCollections = collectionRepository.all()
        assertThat(allCollections.size).isEqualTo(1)
    }

    @Test
    fun `do not throw an exception when the requested collection to be deleted does not exist`() = runTest {
        val newCollection: CollectionNew = CollectionNew.Def(
            name = "My Collection",
            media = listOf(SingleMediaItem.Movie("movie #1"))
        )
        collectionRepository.run {
            upsert(newCollection)
            collectionLocalDataSource.clearCache()
            delete(newCollection)
        }

        val allCollections = collectionRepository.all()
        assertThat(allCollections.size).isEqualTo(0)
    }

    @Test
    fun `throw an exception when a failure occurs while trying to delete a collection`() = runTest {
        val newCollection: CollectionNew = CollectionNew.Def(
            name = "My Collection",
            media = listOf(SingleMediaItem.Movie("movie #1"))
        )
        val newCollection2: CollectionNew = CollectionNew.Def(
            name = "My Collection #2",
            media = listOf(SingleMediaItem.Movie("movie #1"))
        )

        collectionRepository.run {
            upsert(newCollection)
            upsert(newCollection2)

            collectionLocalDataSource.forceFailure = true
            assertFailsWith<Exception> { delete(newCollection) }
        }
    }

}