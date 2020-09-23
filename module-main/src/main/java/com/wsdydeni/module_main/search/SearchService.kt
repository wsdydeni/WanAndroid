package com.wsdydeni.module_main.search

import com.wsdydeni.library_base.network.ApiResponse
import com.wsdydeni.module_main.bean.ArticleList
import com.wsdydeni.module_main.bean.SearchInfoItem
import retrofit2.http.*

interface SearchService {

    @GET("hotkey/json")
    suspend fun getHotSearch() : ApiResponse<ArrayList<SearchInfoItem>>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    suspend fun search(@Path("page") page: Int, @Field("k") key: String): ApiResponse<ArticleList>
}