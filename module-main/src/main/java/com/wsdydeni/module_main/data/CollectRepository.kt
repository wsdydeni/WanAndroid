package com.wsdydeni.module_main.data

import com.wsdydeni.library_base.base.relateViewCommon
import com.wsdydeni.library_base.network.NetworkApiService
import com.wsdydeni.library_base.network.Repository
import com.wsdydeni.module_main.api.CollectService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CollectRepository : Repository {

    private val service by lazy { NetworkApiService.getService(CollectService::class.java,true) }

    fun getCollectArticleList(page: Int) = flow {
        relateViewCommon {
            service.getCollectListArticle(page)
        }.collect {
            emit(it)
        }
    }.flowOn(Dispatchers.IO)
}