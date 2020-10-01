package com.wsdydeni.module_sign.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.wsdydeni.library_base.base.LiveCoroutinesViewModel
import com.wsdydeni.library_base.network.RequestState
import com.wsdydeni.module_sign.data.LoginRepository
import com.wsdydeni.module_sign.model.User

class LoginViewModel : LiveCoroutinesViewModel() {

    val repository by lazy { LoginRepository() }

    val userName = ObservableField("")
    val passWord = ObservableField("")

    private val _login = MutableLiveData<Boolean>()

    val user1 = MutableLiveData<RequestState<User>>()

    fun login() {
        _login.value = true
    }

}