package com.wsdydeni.module_main.ui.me

import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.library_base.base.BaseViewModel

class MeViewModel : BaseViewModel() {


    fun onClick(position: Int) {
        if(position == 1) {
            ARouter.getInstance().build("/main/DarkModeActivity").navigation()
        }
    }
}