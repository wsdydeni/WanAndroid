package com.wsdydeni.module_home

import com.wsdydeni.library_base.network.BaseRepository
import kotlinx.coroutines.CoroutineScope

class HomeRepository(viewModelScope: CoroutineScope): BaseRepository<HomeService>(viewModelScope,HomeService::class.java) {

    suspend fun getBanner() = service.getBanner()
}

