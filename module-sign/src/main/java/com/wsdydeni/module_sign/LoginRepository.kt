package com.wsdydeni.module_sign

import com.wsdydeni.library_base.network.BaseRepository
import com.wsdydeni.library_base.network.NetworkApiService
import kotlinx.coroutines.CoroutineScope

class LoginRepository(viewModelScope: CoroutineScope) : BaseRepository(viewModelScope) {
    private val service by lazy { NetworkApiService.getService(LoginService::class.java,true) }

    suspend fun login(userName : String,password: String) = service.login(userName,password)
}