package com.wsdydeni.module_sign.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.wsdydeni.library_base.base.LiveCoroutinesViewModel
import com.wsdydeni.library_base.base.StatefulLiveData
import com.wsdydeni.module_sign.data.LoginRepository
import com.wsdydeni.module_sign.model.User

class LoginViewModel : LiveCoroutinesViewModel() {

    private val repository by lazy { LoginRepository() }

    val userName = ObservableField("")
    val passWord = ObservableField("")

    private val _login = MutableLiveData(false)

    var user : StatefulLiveData<User> = _login.switchMap {
        launchOnViewModelRequestState { repository.login(userName.get() ?: "", passWord.get() ?: "").asLiveData() }
    }

    fun login() {
        _login.value = true
    }

}