package com.wsdydeni.module_home

import com.alibaba.android.arouter.facade.template.IProvider

interface ISkill : IProvider {
    fun setNavigationVisibility(visible: Boolean)
}