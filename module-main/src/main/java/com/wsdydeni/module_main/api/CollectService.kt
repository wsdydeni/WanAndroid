package com.wsdydeni.module_main.api

import com.wsdydeni.library_base.network.ApiResponse
import com.wsdydeni.module_main.model.CollectList
import retrofit2.http.GET
import retrofit2.http.Path

interface CollectService {
    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectListArticle(@Path("page") page: Int) : ApiResponse<CollectList>
}