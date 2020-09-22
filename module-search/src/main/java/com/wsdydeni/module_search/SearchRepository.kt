package com.wsdydeni.module_search

import com.wsdydeni.library_base.network.BaseRepository
import com.wsdydeni.library_base.network.NetworkApiService
import kotlinx.coroutines.CoroutineScope

class SearchRepository(viewModelScope: CoroutineScope): BaseRepository(viewModelScope) {

    private val service by lazy { NetworkApiService.getService(SearchService::class.java,false) }

    suspend fun getHotSearch() = service.getHotSearch()

    suspend fun search(page: Int,key: String) = service.search(page, key)
}