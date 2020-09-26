package com.wsdydeni.library_base.network

sealed class RequestState<out T> {
    object Loading : RequestState<Nothing>()
    data class Success<out T>(val data: T?) : RequestState<T>()
    data class Error(val error: ApiException? = null) : RequestState<Nothing>()

}