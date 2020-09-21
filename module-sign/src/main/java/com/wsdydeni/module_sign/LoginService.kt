package com.wsdydeni.module_sign

import com.wsdydeni.library_base.network.ApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String) : ApiResponse<User>
}