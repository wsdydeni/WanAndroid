package com.wsdydeni.module_sign

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wsdydeni.library_base.base.BaseViewModel
import com.wsdydeni.library_base.network.ApiResponse
import com.wsdydeni.library_base.network.RequestState
import com.wsdydeni.library_base.network.launchRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi

class LoginViewModel : BaseViewModel() {

    private val repository by lazy { LoginRepository(viewModelScope) }

    val userName = ObservableField("")
    val passWord = ObservableField("")

    val user = MutableLiveData<RequestState<ApiResponse<User>>>()

    @ExperimentalCoroutinesApi
    fun login() = repository.launchRequest(suspend { repository.login(userName.get() ?: "", passWord.get() ?: "") }, user)
}