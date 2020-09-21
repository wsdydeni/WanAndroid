package com.wsdydeni.module_main.home

import com.alibaba.android.arouter.facade.template.IProvider

interface ISkill : IProvider {
    fun setNavigationVisibility(visible: Boolean)
}