package com.sunrisekcdeveloper.medialion.components.discovery.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchSuggestedMediaUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaRequirementsFactory
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.TitledMediaRepository
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.CollectionRepositoryNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.SingleMediaItemRepository
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchSuggestedMediaUseCaseTest {

    private lateinit var fetchSuggestedMediaUseCase: FetchSuggestedMediaUseCase
    private lateinit var collectionRepository: CollectionRepositoryNew.Fake
    private lateinit var mediaRequirementsFactory: MediaRequirementsFactory.Fake
    private lateinit var titledMediaRepository: TitledMediaRepository.Fake

    @BeforeTest
    fun setup() {
        mediaRequirementsFactory = MediaRequirementsFactory.Fake()
        collectionRepository = CollectionRepositoryNew.Fake()
        titledMediaRepository = TitledMediaRepository.Fake()
        fetchSuggestedMediaUseCase = FetchSuggestedMediaUseCase.Def(
            collectionRepository = collectionRepository,
            mediaRequirementsFactory = mediaRequirementsFactory,
            titledMediaRepository = titledMediaRepository
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
            .map { SingleMediaItem.Movie("Item #$it") }
        collectionRepository.upsert(
            CollectionNew.Def("Watch Later").apply {
                add(mediaItems[2])
                add(mediaItems[4])
                add(mediaItems[6])
            }
        )

        titledMediaRepository.providePoolOfMedia(mediaItems)

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
        titledMediaRepository.forceFailure = true

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