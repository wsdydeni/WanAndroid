package com.wsdydeni.module_main.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wsdydeni.library_base.base.BaseViewModel
import com.wsdydeni.library_base.network.ApiResponse
import com.wsdydeni.library_base.network.RequestState
import com.wsdydeni.library_base.network.launchRequest
import com.wsdydeni.module_main.bean.ArticleList
import com.wsdydeni.module_main.bean.SearchInfoItem

class SearchViewModel : BaseViewModel() {

    private val repository by lazy { SearchRepository(viewModelScope) }

    val hotSearchItems = MutableLiveData<RequestState<ApiResponse<ArrayList<SearchInfoItem>>>>()

    val searchItems = MutableLiveData<RequestState<ApiResponse<ArticleList>>>()

    fun getHotSearch() = repository.launchRequest(suspend { repository.getHotSearch() },hotSearchItems)

    fun search(page: Int,key: String) = repository.launchRequest(suspend { repository.search(page, key) },searchItems)
}