package com.sunrisekcdeveloper.medialion.newarch.search

import com.sunrisekcdeveloper.medialion.newarch.search.usecase.SearchForMediaError

sealed interface ApiCallError : SearchForMediaError
data class HttpUnsuccessfulCodeError(val code: Int) : ApiCallError
object RemoveServerNotReached : ApiCallError
object UnexpectedApiCommunicationError : ApiCallError