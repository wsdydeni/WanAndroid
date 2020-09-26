package com.wsdydeni.library_base.base

import com.wsdydeni.library_base.network.ApiException
import com.wsdydeni.library_base.network.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * @description：Flow Repo
 */
fun <T> flowRequest(request: suspend () -> BaseResponse<T>): Flow<T> {
    return flow {
        val response = request()

        when (response.getResponseCode()) {
            0 -> {
                emit(response.getResponseData())
            }
            else -> {
                throw ApiException(response.getResponseMsg(), response.getResponseCode())
            }
        }

    }.flowOn(Dispatchers.IO)
}

fun <T> flowRequestNoCheck(request: suspend () -> T): Flow<T> {
    return flow {
        emit(request())
    }.flowOn(Dispatchers.IO)
}