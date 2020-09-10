package com.wsdydeni.library_base.network

sealed class RequestState<out T> {
    object Loading : RequestState<Nothing>()
    object Empty : RequestState<Nothing>()
    data class Success<out T>(val data: T) : RequestState<T>()
    data class Error(val error : Exception? = null) : RequestState<Nothing>()
}