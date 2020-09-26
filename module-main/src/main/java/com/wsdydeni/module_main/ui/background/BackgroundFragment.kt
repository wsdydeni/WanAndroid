package com.wsdydeni.module_main.ui.background

import android.util.Log
import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R

class BackgroundFragment : BaseFragment() {

    private val backgroundViewModel by lazy { BackgroundViewModel() }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_background,BR.viewModel,backgroundViewModel)
    }

    override fun initView() {

    }

    override fun initData() {
        Log.e("BackgroundFragment","发起请求")
    }

    override fun startObserve() {

    }
}