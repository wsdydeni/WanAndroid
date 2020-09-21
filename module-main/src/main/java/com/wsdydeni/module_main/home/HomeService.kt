package com.wsdydeni.module_main.home

import com.wsdydeni.library_base.network.ApiResponse
import com.wsdydeni.library_view.banner.BannerInfo
import com.wsdydeni.module_main.home.popular.bean.Article
import com.wsdydeni.module_main.home.popular.bean.ArticleList
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService  {

    @GET("banner/json")
    suspend fun getBanner() : ApiResponse<List<BannerInfo>>

    @GET("article/top/json")
    suspend fun getTopArticles() : ApiResponse<ArrayList<Article>>

    @GET("article/list/{page}/json")
    suspend fun getListArticles(@Path("page") page: Int) : ApiResponse<ArticleList>

    @GET("article/listproject/{page}/json")
    suspend fun getTopProjects(@Path("page") page: Int) : ApiResponse<ArticleList>
}