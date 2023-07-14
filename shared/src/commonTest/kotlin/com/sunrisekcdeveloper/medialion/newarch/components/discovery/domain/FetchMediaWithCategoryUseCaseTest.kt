package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain

import assertk.assertThat
import assertk.assertions.isNotNull
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaRequirementsFactory
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.repo.MediaCategoryRepository
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.repo.TitledMediaRepository
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchMediaWithCategoryUseCaseTest {

    private lateinit var fetchMediaWithCategoryUseCase: FetchMediaWithCategoryUseCase
    private lateinit var mediaCategoryRepository: MediaCategoryRepository.Fake
    private lateinit var titledMediaRepository: TitledMediaRepository.Fake
    private lateinit var mediaRequirementsFactory: MediaRequirementsFactory.Fake

    @BeforeTest
    fun setup() {
        mediaCategoryRepository = MediaCategoryRepository.Fake()
        titledMediaRepository = TitledMediaRepository.Fake()
        mediaRequirementsFactory = MediaRequirementsFactory.Fake()
        fetchMediaWithCategoryUseCase = FetchMediaWithCategoryUseCase.D(
            titledMediaRepo = titledMediaRepository,
            mediaRequirementsFactory = mediaRequirementsFactory
        )
    }

    @Test
    fun `when invoked return success with a list of media`() = runTest {
        val (success, _) = fetchMediaWithCategoryUseCase(mediaCategoryRepository.getRandomOrAll(1).first())

        assertThat(success).isNotNull()
        assertThat(success).instanceOf(Ok::class)
    }

    @Test
    fun `when an exception occurs when creating a requirements object return a failure object`() = runTest {
        mediaRequirementsFactory.forceFailure = true
        val (_, failure) = fetchMediaWithCategoryUseCase(mediaCategoryRepository.getRandomOrAll(1).first())

        assertThat(failure).isNotNull()
        assertThat(failure).instanceOf(Err::class)
    }

    @Test
    fun `when an exception occurs when fetching titled media object return a failure object`() = runTest {
        titledMediaRepository.forceFailure = true
        val (_, failure) = fetchMediaWithCategoryUseCase(mediaCategoryRepository.getRandomOrAll(1).first())

        assertThat(failure).isNotNull()
        assertThat(failure).instanceOf(Err::class)
    }

}