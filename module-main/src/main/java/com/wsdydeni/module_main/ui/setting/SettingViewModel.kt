package com.wsdydeni.module_main.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wsdydeni.library_base.base.BaseViewModel
import com.wsdydeni.library_base.network.NetworkApiService
import com.wsdydeni.library_base.network.executeResponse
import com.wsdydeni.library_base.network.suspendOnFailure
import com.wsdydeni.library_base.network.suspendOnSuccess
import com.wsdydeni.module_main.api.SettingService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SettingViewModel : BaseViewModel() {

    var isLogoutSuccess = MutableLiveData<Boolean>()

    private val service by lazy { NetworkApiService.getService(SettingService::class.java,false) }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            executeResponse(service.logout()).suspendOnSuccess {
                withContext(Dispatchers.Main) { isLogoutSuccess.value = true }
            }.suspendOnFailure {
                withContext(Dispatchers.Main) { isLogoutSuccess.value = false }
            }
        }
    }
}