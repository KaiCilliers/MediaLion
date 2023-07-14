package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain

import assertk.assertThat
import assertk.assertions.isNotNull
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.repo.MediaCategoryRepository
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class FetchMediaCategoriesUseCaseTest {

    private lateinit var fetchAllMediaCategoriesUseCase: FetchAllMediaCategoriesUseCase
    private lateinit var mediaCategoryRepository: MediaCategoryRepository.Fake

    @BeforeTest
    fun setup() {
        mediaCategoryRepository = MediaCategoryRepository.Fake()
        fetchAllMediaCategoriesUseCase = FetchAllMediaCategoriesUseCase.D(
            mediaCategoryRepository = mediaCategoryRepository
        )
    }

    @Test
    fun `return all media categories on success`() = runTest {
        val (success, _) = fetchAllMediaCategoriesUseCase()

        assertThat(success).isNotNull()
        assertThat(success).instanceOf(Ok::class)
    }

    @Test
    fun `return a failure when an exception occurs`() = runTest {
        mediaCategoryRepository.forceFailure = true
        val (_, failure) = fetchAllMediaCategoriesUseCase()

        assertThat(failure).isNotNull()
        assertThat(failure).instanceOf(Err::class)
    }

}