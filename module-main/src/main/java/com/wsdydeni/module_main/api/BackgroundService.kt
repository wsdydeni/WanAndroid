package com.wsdydeni.module_main.api

import com.wsdydeni.library_base.network.ApiResponse
import com.wsdydeni.module_main.model.ArticleList
import com.wsdydeni.module_main.model.Tree
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface BackgroundService {

    @GET("tree/json") suspend fun getTree() : ApiResponse<ArrayList<Tree>>

    @GET("article/list/{page}/json")
    suspend fun getSystemTypeDetail(@Path("page") page: Int, @Query("cid") cid: Int): ApiResponse<ArticleList>
}