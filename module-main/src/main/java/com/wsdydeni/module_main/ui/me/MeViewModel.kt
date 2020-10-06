package com.wsdydeni.module_main.ui.me

import androidx.lifecycle.MutableLiveData
import com.wsdydeni.library_base.base.BaseViewModel
import com.wsdydeni.module_main.model.User

class MeViewModel : BaseViewModel() {

    var user = MutableLiveData<User>()
}