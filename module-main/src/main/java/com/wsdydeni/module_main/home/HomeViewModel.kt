package com.wsdydeni.module_main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wsdydeni.library_base.base.BaseViewModel
import com.wsdydeni.library_base.network.ApiResponse
import com.wsdydeni.library_base.network.RequestState
import com.wsdydeni.library_base.network.launchRequest
import com.wsdydeni.library_view.banner.BannerInfo
import com.wsdydeni.module_main.bean.Article
import com.wsdydeni.module_main.bean.ArticleList

class HomeViewModel : BaseViewModel() {

    private val repository by lazy { HomeRepository(viewModelScope) }

    val banner = MutableLiveData<RequestState<ApiResponse<List<BannerInfo>>>>()

    val topArticles = MutableLiveData<RequestState<ApiResponse<ArrayList<Article>>>>()

    val topProjects = MutableLiveData<RequestState<ApiResponse<ArticleList>>>()

    val listArticles = MutableLiveData<RequestState<ApiResponse<ArticleList>>>()

    fun getBanner() = repository.launchRequest(suspend { repository.getBanner() },banner)


    fun getTopArticles() = repository.launchRequest(suspend { repository.getTopArticles() },topArticles)


    fun getTopProjects(page : Int) = repository.launchRequest(suspend { repository.getTopProjects(page) },topProjects)


    fun getListProjects(page: Int) = repository.launchRequest(suspend { repository.getListArticles(page) },listArticles)

}