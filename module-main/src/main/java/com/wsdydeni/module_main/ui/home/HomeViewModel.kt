package com.wsdydeni.module_main.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.wsdydeni.library_base.base.LiveCoroutinesViewModel
import com.wsdydeni.module_main.data.HomeRepository

class HomeViewModel : LiveCoroutinesViewModel() {

    private var curArticlePage = 0

    private var curProjectPage = 0

    private val repository by lazy { HomeRepository() }

    private val _bannerList = MutableLiveData(false)

    private val _topArticles = MutableLiveData(false)

    private val _listProjects = MutableLiveData(false)

    private val _listArticle = MutableLiveData(false)

    var bannerList = _bannerList.switchMap {
        launchOnViewModelScope { repository.getBanner().asLiveData() }
    }

    var topArticles = _topArticles.switchMap {
        launchOnViewModelScope { repository.getTopArticles().asLiveData() }
    }

    var listArticles = _listArticle.switchMap {
        launchOnViewModelScope { repository.getListArticles(curArticlePage).asLiveData() }
    }

    var listProjects  = _listProjects.switchMap {
        launchOnViewModelScope { repository.getListProjects(curProjectPage).asLiveData() }
    }

    fun getBannerList() { _bannerList.value = true }

    fun getTopArticle() { _topArticles.value = true }

    fun getListProjects(page: Int)  {
        curProjectPage = page
        _listProjects.value = true
    }

    fun getListArticle(page: Int) {
        curArticlePage = page
        _listArticle.value = true
    }

}