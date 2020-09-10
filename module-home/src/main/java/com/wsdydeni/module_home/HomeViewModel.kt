package com.wsdydeni.module_home

import androidx.lifecycle.viewModelScope
import com.wsdydeni.library_base.base.BaseViewModel

class HomeViewModel : BaseViewModel() {
    val repository by lazy { HomeRepository(viewModelScope) }
}