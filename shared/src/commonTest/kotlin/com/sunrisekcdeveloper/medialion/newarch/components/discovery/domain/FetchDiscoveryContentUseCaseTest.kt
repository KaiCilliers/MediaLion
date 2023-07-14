package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain

import assertk.assertThat
import assertk.assertions.isGreaterThan
import assertk.assertions.isNotNull
import com.github.michaelbull.result.Ok
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.DiscoveryPage
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.repo.MediaRequirementsRepository
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.repo.TitledMediaRepository
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchDiscoveryContentUseCaseTest {

    private lateinit var fetchDiscoveryContentUseCase: FetchDiscoveryContentUseCase
    private lateinit var mediaRequirementsRepo: MediaRequirementsRepository.Fake
    private lateinit var titledMediaRepo: TitledMediaRepository.Fake

    @BeforeTest
    fun setup() {
        mediaRequirementsRepo = MediaRequirementsRepository.Fake()
        titledMediaRepo = TitledMediaRepository.Fake()
        fetchDiscoveryContentUseCase = FetchDiscoveryContentUseCase.D(
            mediaRequirementsRepo = mediaRequirementsRepo,
            titledMediaRepo = titledMediaRepo
        )
    }

    @Test
    fun `when invoked return success with a list of media`() = runTest {
        val (success, _) = fetchDiscoveryContentUseCase(DiscoveryPage.All)

        assertThat(success).isNotNull()
        assertThat(success).instanceOf(Ok::class)
        assertThat(success!!.collectionNames().size).isGreaterThan(0)
    }

    @Test
    fun `when an exception occurs return a failure object`() = runTest {
        mediaRequirementsRepo.forceFailure = true
        val (_, failure) = fetchDiscoveryContentUseCase(DiscoveryPage.All)

        assertThat(failure).instanceOf(FetchDiscoveryContentError::class)
    }
}