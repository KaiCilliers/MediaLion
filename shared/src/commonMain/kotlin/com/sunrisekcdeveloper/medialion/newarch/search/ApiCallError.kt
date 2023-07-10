package com.sunrisekcdeveloper.medialion.newarch.search

import com.sunrisekcdeveloper.medialion.newarch.mycollection.usecase.FetchMyCollectionsError
import com.sunrisekcdeveloper.medialion.newarch.search.usecase.SearchForMediaError

sealed interface ApiCallError : SearchForMediaError, FetchMyCollectionsError
data class HttpUnsuccessfulCodeError(val code: Int) : ApiCallError
object RemoveServerNotReached : ApiCallError
object UnexpectedApiCommunicationError : ApiCallError