package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.repo

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaRequirements
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos.SingleMediaItemRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class TitledMediaRepositoryTest {

    private lateinit var titledMediaRepository: TitledMediaRepository
    private lateinit var singleMediaItemRepository: SingleMediaItemRepository.Fake

    @BeforeTest
    fun setup() {
        singleMediaItemRepository = SingleMediaItemRepository.Fake()
        titledMediaRepository = TitledMediaRepository.D(
            singleMediaItemRepository = singleMediaItemRepository
        )
    }

    @Test
    fun `return list of media with title from the requirements object`() = runTest {
        val titledMedia = titledMediaRepository.withRequirement(
            MediaRequirements(withTitle = Title("123"))
        )
        assertThat(titledMedia.title()).isEqualTo(Title("123"))
    }

    @Test
    fun `throw an exception when any error occurs`() = runTest {
        singleMediaItemRepository.forceFailure = true
        assertFailsWith<Exception> {
            titledMediaRepository.withRequirement(MediaRequirements(withTitle = Title("123")))
        }
    }

}