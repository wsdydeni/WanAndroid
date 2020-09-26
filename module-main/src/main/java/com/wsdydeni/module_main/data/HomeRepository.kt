package com.wsdydeni.module_main.data

import com.wsdydeni.library_base.base.relateViewCommon
import com.wsdydeni.library_base.network.NetworkApiService
import com.wsdydeni.library_base.network.Repository
import com.wsdydeni.module_main.api.HomeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeRepository : Repository {

    private val service by lazy { NetworkApiService.getService(HomeService::class.java,false) }

    suspend fun getBanner() = flow {
        relateViewCommon { service.getBanner() }.collect { emit(it) }
    }.flowOn(Dispatchers.IO)

    suspend fun getTopArticles() = flow {
        relateViewCommon {  service.getTopArticles() }.collect { emit(it) }
    }.flowOn(Dispatchers.IO)

    suspend fun getListArticles(page : Int) = flow {
        relateViewCommon { service.getListArticles(page) }.collect { emit(it) }
    }.flowOn(Dispatchers.IO)

    suspend fun getListProjects(page : Int) = flow {
        relateViewCommon { service.getListProjects(page) }.collect { emit(it) }
    }.flowOn(Dispatchers.IO)
}


