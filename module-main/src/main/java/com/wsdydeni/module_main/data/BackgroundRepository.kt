package com.wsdydeni.module_main.data

import com.wsdydeni.library_base.base.relateViewCommon
import com.wsdydeni.library_base.network.NetworkApiService
import com.wsdydeni.module_main.api.BackgroundService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class BackgroundRepository {

    private val service by lazy { NetworkApiService.getService(BackgroundService::class.java,false) }

    suspend fun getTree() = flow {
        relateViewCommon {
            service.getTree()
        }.collect {
            emit(it)
        }
    }.flowOn(Dispatchers.IO)

}