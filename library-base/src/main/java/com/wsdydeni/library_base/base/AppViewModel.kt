package com.wsdydeni.library_base.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

object AppViewModel : AndroidViewModel(BaseApplication.getApplication()) {
    val loginState = MutableLiveData(false)
}