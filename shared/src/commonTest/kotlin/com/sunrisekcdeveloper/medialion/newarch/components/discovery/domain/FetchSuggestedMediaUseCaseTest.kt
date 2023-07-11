package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos.CollectionRepositoryNew
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchSuggestedMediaUseCaseTest {

    private lateinit var fetchSuggestedMediaUseCase: FetchSuggestedMediaUseCase
    private lateinit var collectionRepository: CollectionRepositoryNew.Fake
    private lateinit var singleMediaItemRepository: SingleMediaItemRepository.Fake

    @BeforeTest
    fun setup() {
        collectionRepository = CollectionRepositoryNew.Fake()
        singleMediaItemRepository = SingleMediaItemRepository.Fake()
        fetchSuggestedMediaUseCase = FetchSuggestedMediaUseCase.Def(
            singleMediaItemRepository = singleMediaItemRepository,
            collectionRepository = collectionRepository,
        )
    }

    @Test
    fun `there should be exactly 32 media items on a successful invocation`() = runTest {
        val (success, _) = fetchSuggestedMediaUseCase()

        assertThat(success).isNotNull()
        assertThat(success!!.media().size).isEqualTo(32)
        assertThat(success.title().value).isEqualTo("Suggested Media")
    }

    @Test
    fun `media retrieved should not contain any media found in user's collections`() = runTest {
        val mediaItems = (0..100)
            .map { SingleMediaItem.Def("Item #$it") }
        collectionRepository.upsert(
            CollectionNew.Def("Watch Later").apply {
                add(mediaItems[2])
                add(mediaItems[4])
                add(mediaItems[6])
            }
        )

        singleMediaItemRepository.providePoolOfMedia(mediaItems)

        val (success, _) = fetchSuggestedMediaUseCase()

        assertThat(success).isNotNull()
        assertThat(success!!.media().size).isEqualTo(32)
        assertThat(success.title().value).isEqualTo("Suggested Media")
        assertThat(success.media().contains(mediaItems[2])).isFalse()
        assertThat(success.media().contains(mediaItems[4])).isFalse()
        assertThat(success.media().contains(mediaItems[6])).isFalse()
        assertThat(success.media().map { it.name().value }.contains(mediaItems[2].name().value)).isFalse()
        assertThat(success.media().map { it.name().value }.contains(mediaItems[4].name().value)).isFalse()
        assertThat(success.media().map { it.name().value }.contains(mediaItems[6].name().value)).isFalse()
    }

    @Test
    fun `return a failure when an exception occurs when invoked`() = runTest {
        collectionRepository.forceFailure = false
        singleMediaItemRepository.forceFailure = true

        val result = fetchSuggestedMediaUseCase()
        assertThat(result.getError()).isNotNull()
        assertThat(result.getError()).instanceOf(Err::class)
    }

    @Test
    fun `do not return a failure when an exception occurs when accessing collections`() = runTest {
        collectionRepository.forceFailure = true

        val result = fetchSuggestedMediaUseCase()
        assertThat(result.get()).isNotNull()
        assertThat(result.get()).instanceOf(Ok::class)
    }

}