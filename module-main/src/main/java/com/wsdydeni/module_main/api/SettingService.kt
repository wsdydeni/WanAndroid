package com.wsdydeni.module_main.api

import com.wsdydeni.library_base.network.ApiResponse
import retrofit2.http.GET


interface SettingService {
    @GET("user/logout/json") suspend fun logout() : ApiResponse<Any>
}