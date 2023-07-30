package com.sunrisekcdeveloper.medialion.features.shared

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.doesNotContain
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.sunrisekcdeveloper.medialion.components.collections.domain.AddUpdateCollectionUseCase
import com.sunrisekcdeveloper.medialion.components.collections.domain.DeleteCollectionUseCaseNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.FetchAllCollectionsUseCaseNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.CollectionRepositoryNew
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
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
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class MLMiniCollectionViewModelTest {

    private lateinit var sut: MLMiniCollectionViewModel
    private lateinit var fetchAllCollectionsUseCase: FetchAllCollectionsUseCaseNew
    private lateinit var deleteCollectionUseCase: DeleteCollectionUseCaseNew
    private lateinit var addUpdateCollectionUseCase: AddUpdateCollectionUseCase
    private lateinit var collectionRepository: CollectionRepositoryNew.Fake

    private val scope = TestScope()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher(scope.testScheduler))
        collectionRepository = CollectionRepositoryNew.Fake()
        addUpdateCollectionUseCase = AddUpdateCollectionUseCase.Def(
            collectionRepository = collectionRepository,
        )
        deleteCollectionUseCase = DeleteCollectionUseCaseNew.Def(
            collectionRepository = collectionRepository
        )
        fetchAllCollectionsUseCase = FetchAllCollectionsUseCaseNew.Default(
            collectionRepository = collectionRepository,
        )
        sut = MLMiniCollectionViewModel.D(
            fetchAllCollectionsUseCase = fetchAllCollectionsUseCase,
            deleteCollectionUseCase = deleteCollectionUseCase,
            addUpdateCollectionUseCase = addUpdateCollectionUseCase,
            coroutineScope = scope,
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `after observing receive a list of collections`() = runTest {
        sut.miniCollectionState.test {
            assertThat(awaitItem()).instanceOf(Loading::class)
            val content = awaitItem()
            assertThat(content).instanceOf(Content::class)
        }
    }

    @Test
    fun `when a delete collection action is submitted the collection list is updated`() = runTest {
        val collectionToDelete = CollectionNew.Def("Collection #2")
        collectionRepository.upsert(CollectionNew.Def("Collection #1"))
        collectionRepository.upsert(collectionToDelete)
        collectionRepository.upsert(CollectionNew.Def("Collection #3"))

        sut.miniCollectionState.test {
            awaitItem()

            var content = awaitItem()
            assertThat(content).instanceOf(Content::class)
            assertThat((content as Content).collections).contains(collectionToDelete)

            sut.submit(DeleteCollection(collectionToDelete))
            content = awaitItem()

            assertThat(content).instanceOf(Content::class)
            assertThat((content as Content).collections).doesNotContain(collectionToDelete)
        }
    }

    @Test
    fun `when adding media to a collection and submitting an update action then the collection list is updated`() = runTest {
        val collectionToUpdate = CollectionNew.Def("Collection #2")
        val movieToAdd = SingleMediaItem.Movie("Movie")

        collectionRepository.upsert(CollectionNew.Def("Collection #1"))
        collectionRepository.upsert(collectionToUpdate)
        collectionRepository.upsert(CollectionNew.Def("Collection #3"))

        sut.miniCollectionState.test {
            awaitItem()

            var content = awaitItem()
            assertThat(content).instanceOf(Content::class)
            assertThat((content as Content).collections).contains(collectionToUpdate)

            collectionToUpdate.add(movieToAdd)

            sut.submit(UpdateCollection(collectionToUpdate))
            content = awaitItem()

            assertThat(content).instanceOf(Content::class)
            assertThat((content as Content).collections.first { it.title() == collectionToUpdate.title() }.media()).contains(movieToAdd)
        }
    }

    @Test
    fun `when a create collection action is submitted the collection list is updated`() = runTest {
        sut.miniCollectionState.test {
            awaitItem()

            var content = awaitItem()
            assertThat(content).instanceOf(Content::class)

            sut.submit(CreateCollection(Title("New Collection")))
            content = awaitItem()

            assertThat(content).instanceOf(Content::class)
            assertThat((content as Content).collections.first { it.title() == Title("New Collection") }).isNotNull()
        }
    }

    @Test
    fun `when inserting a new collection it is to appear as the first item in the new state`() = runTest {
        sut.miniCollectionState.test {
            awaitItem()

            var content = awaitItem()
            assertThat(content).instanceOf(Content::class)

            sut.submit(CreateCollection(Title("New Collection")))
            content = awaitItem()

            assertThat(content).instanceOf(Content::class)
            assertEquals((content as Content).collections[0].title(), Title("New Collection"))
        }
    }

    @Test
    fun `when updating the collection content the new state that is received is in the same order as the previous state`() = runTest {
        sut.miniCollectionState.test {
            awaitItem()

            var content = awaitItem()
            assertThat(content).instanceOf(Content::class)

            val collectionToUpdate = CollectionNew.Def("Collection to be updated")
            sut.submit(InsertCollection(collectionToUpdate))
            content = awaitItem()

            sut.submit(CreateCollection(Title("Latest Collection")))
            content = awaitItem()

            assertThat(content).instanceOf(Content::class)
            assertEquals((content as Content).collections[0].title(), Title("Latest Collection"))

            collectionToUpdate.add(SingleMediaItem.Movie("A new movie"))

            sut.submit(UpdateCollection(collectionToUpdate))
            content = awaitItem()

            assertThat(content).instanceOf(Content::class)
            assertEquals(Title("Collection to be updated"), (content as Content).collections[1].title())
            assertThat((content as Content).collections[1].media().size).isEqualTo(1)
        }
    }

}