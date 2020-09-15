package com.wsdydeni.module_home

import com.wsdydeni.library_base.network.ApiResponse
import com.wsdydeni.library_view.banner.BannerInfo
import com.wsdydeni.module_home.article.Article
import retrofit2.http.GET

interface HomeService  {

    @GET("banner/json")
    suspend fun getBanner() : ApiResponse<List<BannerInfo>>

    @GET("article/top/json")
    suspend fun getTopArticles() : ApiResponse<List<Article>>
}