package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models

sealed interface ApiCallError
data class HttpUnsuccessfulCodeError(val code: Int) : ApiCallError
object RemoveServerNotReached : ApiCallError
object UnexpectedApiCommunicationError : ApiCallError

sealed interface LocalCacheError
object DatabaseAccessError : LocalCacheError
object ResourceDoesNotExist : LocalCacheError
