package com.wsdydeni.module_sign.api

import com.wsdydeni.library_base.network.ApiResponse
import com.wsdydeni.library_base.network.RequestState
import com.wsdydeni.module_sign.model.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String) : ApiResponse<RequestState<User>>
}