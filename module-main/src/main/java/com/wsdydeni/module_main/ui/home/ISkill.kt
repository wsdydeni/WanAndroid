package com.wsdydeni.module_main.ui.home

import com.alibaba.android.arouter.facade.template.IProvider

interface MainIS : IProvider {
    fun setNavigationVisibility(visible: Boolean)
}