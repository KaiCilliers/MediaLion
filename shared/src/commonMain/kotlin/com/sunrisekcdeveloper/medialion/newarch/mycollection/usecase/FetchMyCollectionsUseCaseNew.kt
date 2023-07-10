package com.sunrisekcdeveloper.medialion.newarch.mycollection.usecase

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.sunrisekcdeveloper.medialion.newarch.mycollection.models.TitledMediaList

interface FetchMyCollectionsUseCaseNew {
    suspend operator fun invoke(): Result<TitledMediaList, FetchMyCollectionsError>

    class Fake : FetchMyCollectionsUseCaseNew {
        var hinder = false
        override suspend fun invoke(): Result<TitledMediaList, FetchMyCollectionsError> {
            return if (!hinder) {
                Ok(TitledMediaList.Def(emptyList()))
            } else {
                Err(UnableToRetrieveCollections)
            }
        }
    }
}

interface FetchMyCollectionsError
object UnableToRetrieveCollections : FetchMyCollectionsError