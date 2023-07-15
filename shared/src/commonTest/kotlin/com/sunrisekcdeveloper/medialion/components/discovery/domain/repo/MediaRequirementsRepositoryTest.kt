package com.sunrisekcdeveloper.medialion.components.discovery.domain.repo

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.DiscoveryPage
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.MediaCategoryRepository
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.MediaRequirementsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class MediaRequirementsRepositoryTest {

    private lateinit var mediaRequirementsRepository: MediaRequirementsRepository
    private lateinit var mediaCategoryRepository: MediaCategoryRepository.Fake

    @BeforeTest
    fun setup() {
        mediaCategoryRepository = MediaCategoryRepository.Fake()
        mediaRequirementsRepository = MediaRequirementsRepository.D(
            mediaCategoryRepository = mediaCategoryRepository
        )
    }

    @Test
    fun `return 6 media requirements object when DiscoveryPage_All is received`() = runTest {
        val requirements = mediaRequirementsRepository.getForPage(DiscoveryPage.All)
        assertThat(requirements.size).isEqualTo(6)
    }

    @Test
    fun `return 6 media requirements object when DiscoveryPage_Movies is received`() = runTest {
        val requirements = mediaRequirementsRepository.getForPage(DiscoveryPage.Movies)
        assertThat(requirements.size).isEqualTo(6)
    }

    @Test
    fun `return 6 media requirements object when DiscoveryPage_TVShows is received`() = runTest {
        val requirements = mediaRequirementsRepository.getForPage(DiscoveryPage.TVShows)
        assertThat(requirements.size).isEqualTo(6)
    }

    @Test
    fun `throw an exception when any error occurs`() = runTest {
        mediaCategoryRepository.forceFailure = true
        assertFailsWith<Exception> { mediaRequirementsRepository.getForPage(DiscoveryPage.TVShows) }
    }
}