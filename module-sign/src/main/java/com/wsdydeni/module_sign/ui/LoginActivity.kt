package com.wsdydeni.module_sign.ui

import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.wsdydeni.library_base.Config
import com.wsdydeni.library_base.base.AppViewModel
import com.wsdydeni.library_base.base.BaseApplication
import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.base.observeState
import com.wsdydeni.module_sign.BR
import com.wsdydeni.module_sign.R

@Route(path = "/sign/LoginActivity")
class LoginActivity : BaseVMActivity() {

    private val loginViewModel by lazy { LoginViewModel() }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_login, BR.viewModel,loginViewModel)
    }

    override fun initView() {}

    override fun initData() {}

    override fun startObserve() {
        loginViewModel.user.observeState(this, onLoading = {
            Toast.makeText(this, "正在登陆中", Toast.LENGTH_SHORT).show()
        }, onError = {
            Toast.makeText(this, it?.message ?: "登录失败", Toast.LENGTH_SHORT).show()
        },onSuccess = {
//            BaseApplication.mmkv?.encode(Config.LOGIN_USER,it) ?: throw NullPointerException("MMKV Not initialized")
            BaseApplication.mmkv?.encode(Config.LOGIN_STATE,true) ?: throw NullPointerException("MMKV Not initialized")
            AppViewModel.loginState.value = true
            finish()
        })
    }

}
