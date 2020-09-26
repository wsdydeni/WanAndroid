package com.wsdydeni.module_main.data

import com.wsdydeni.library_base.base.relateViewCommon
import com.wsdydeni.library_base.network.NetworkApiService
import com.wsdydeni.library_base.network.Repository
import com.wsdydeni.module_main.api.SearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchRepository : Repository {

    private val service by lazy { NetworkApiService.getService(SearchService::class.java,false) }

    suspend fun getHotSearch() = flow {
        relateViewCommon {  service.getHotSearch() }.collect { emit(it) }
    }.flowOn(Dispatchers.IO)

    suspend fun search(page: Int,key: String) = flow {
        relateViewCommon {  service.search(page, key) }.collect { emit(it) }
    }.flowOn(Dispatchers.IO)
}