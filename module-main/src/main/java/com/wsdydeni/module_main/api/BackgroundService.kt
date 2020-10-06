package com.wsdydeni.module_main.api

import com.wsdydeni.library_base.network.ApiResponse
import com.wsdydeni.module_main.model.Tree
import retrofit2.http.GET


interface BackgroundService {
    @GET("tree/json") suspend fun getTree() : ApiResponse<ArrayList<Tree>>
}