package com.wsdydeni.module_search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wsdydeni.library_base.base.BaseViewModel
import com.wsdydeni.library_base.network.ApiResponse
import com.wsdydeni.library_base.network.RequestState
import com.wsdydeni.library_base.network.launchRequest
import com.wsdydeni.module_search.bean.SearchInfoItem

class SearchViewModel : BaseViewModel() {

    private val repository by lazy { SearchRepository(viewModelScope) }

    val hotSearchItems = MutableLiveData<RequestState<ApiResponse<ArrayList<SearchInfoItem>>>>()

    fun getHotSearch() = repository.launchRequest(suspend { repository.getHotSearch() },hotSearchItems)
}