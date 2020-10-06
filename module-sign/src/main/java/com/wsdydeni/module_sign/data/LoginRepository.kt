package com.wsdydeni.module_sign.data

import com.wsdydeni.library_base.base.associatedView
import com.wsdydeni.library_base.network.ApiException
import com.wsdydeni.library_base.network.NetworkApiService
import com.wsdydeni.library_base.network.Repository
import com.wsdydeni.module_sign.api.LoginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepository : Repository {

    private val service by lazy { NetworkApiService.getService(LoginService::class.java,true) }

    suspend fun login(userName : String,password: String,onError: suspend (ApiException) -> Unit) = flow {
        associatedView(
            suspend { service.login(userName,password) }, onError
        ).collect {
            emit(it)
        }
    }.flowOn(Dispatchers.IO)
}