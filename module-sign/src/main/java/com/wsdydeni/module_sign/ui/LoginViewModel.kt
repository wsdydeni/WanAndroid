package com.wsdydeni.module_sign.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wsdydeni.library_base.base.LiveCoroutinesViewModel
import com.wsdydeni.module_sign.data.LoginRepository
import com.wsdydeni.module_sign.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : LiveCoroutinesViewModel() {

    private val repository by lazy { LoginRepository() }

    val userName = ObservableField("")
    val passWord = ObservableField("")

    val user = MutableLiveData<User>()

    var loginFailure = MutableLiveData<String>()

    fun login() {
        viewModelScope.launch(viewModelScope.coroutineContext) {
            withContext(Dispatchers.IO) {
                repository.login(userName.get() ?: "", passWord.get() ?: "") {
                    withContext(Dispatchers.Main) {
                        loginFailure.value = it.errorMessage
                    }
                }.collect {
                    withContext(Dispatchers.Main) {
                        user.value = it
                    }
                }
            }
        }
    }
}