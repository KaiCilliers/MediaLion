package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models

import com.sunrisekcdeveloper.medialion.newarch.components.collections.domain.FetchMyCollectionsError
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.SearchForMediaError

sealed interface ApiCallError : SearchForMediaError, FetchMyCollectionsError
data class HttpUnsuccessfulCodeError(val code: Int) : ApiCallError
object RemoveServerNotReached : ApiCallError
object UnexpectedApiCommunicationError : ApiCallError