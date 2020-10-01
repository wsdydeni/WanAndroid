package com.wsdydeni.module_main.ui.me

import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.library_base.base.AppViewModel
import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.utils.StatusUtil
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import kotlinx.android.synthetic.main.fragment_me.*

class MeFragment : BaseFragment() {

    private val meViewModel by lazy { MeViewModel() }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_me,BR.viewModel,meViewModel).addBindingParam(BR.app,AppViewModel)
    }

    override fun initView() {
        StatusUtil.setStatusBarPaddingAndHeight(me_header,this.activity)
    }

    override fun initData() {
        me_go_login.setOnClickListener {
            ARouter.getInstance().build("/sign/LoginActivity").navigation()
        }
    }

    override fun startObserve() {

    }

}