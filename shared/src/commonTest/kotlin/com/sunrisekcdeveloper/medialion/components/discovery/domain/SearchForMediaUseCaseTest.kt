package com.sunrisekcdeveloper.medialion.components.discovery.domain

import assertk.assertThat
import assertk.assertions.isNotNull
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import com.sunrisekcdeveloper.medialion.components.discovery.domain.Failure
import com.sunrisekcdeveloper.medialion.components.discovery.domain.SearchForMediaUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaRequirementsFactory
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.SearchQuery
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.TitledMediaRepository
import com.sunrisekcdeveloper.medialion.components.discovery.domain.factories.SearchQueryFactory
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchForMediaUseCaseTest {

    private lateinit var searchForMediaUseCase: SearchForMediaUseCase
    private lateinit var titledMediaRepository: TitledMediaRepository.Fake
    private lateinit var mediaRequirementsFactory: MediaRequirementsFactory

    @BeforeTest
    fun setup() {
        titledMediaRepository = TitledMediaRepository.Fake()
        mediaRequirementsFactory = MediaRequirementsFactory.D()
        searchForMediaUseCase = SearchForMediaUseCase.Def(
            titledMediaRepository = titledMediaRepository,
            mediaRequirementsFactory = mediaRequirementsFactory,
        )
    }

    @Test
    fun `on successful invocation return success with 32 result items`() = runTest {
        val result = searchForMediaUseCase(SearchQuery.Default("movie"))

        assertThat(result.get()).isNotNull()
        assertThat(result.get()).instanceOf(Ok::class)
    }

    @Test
    fun `when search query should not be executed return appropriate failure`() = runTest {
        val result = searchForMediaUseCase(SearchQueryFactory().asNotExecutable().produce())

        assertThat(result.getError()).isNotNull()
        assertThat(result.getError()).instanceOf(Err::class)
    }

    @Test
    fun `on an unexpected exception return a failure`() = runTest {
        titledMediaRepository.forceFailure = true
        val result = searchForMediaUseCase(SearchQuery.Default("movie"))

        assertThat(result.getError()).isNotNull()
        assertThat(result.getError()).instanceOf(Failure::class)
    }
}