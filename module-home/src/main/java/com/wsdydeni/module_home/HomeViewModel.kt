package com.wsdydeni.module_home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wsdydeni.library_base.base.BaseViewModel
import com.wsdydeni.library_base.network.ApiResponse
import com.wsdydeni.library_base.network.RequestState
import com.wsdydeni.library_base.network.launchRequest
import com.wsdydeni.library_view.banner.BannerInfo

class HomeViewModel : BaseViewModel() {

    private val repository by lazy { HomeRepository(viewModelScope) }

    val banner = MutableLiveData<RequestState<ApiResponse<List<BannerInfo>>>>()

    fun getBanner() {
        repository.launchRequest(suspend { repository.getBanner() },banner)
    }
}