package com.wsdydeni.module_sign

import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.wsdydeni.library_base.Config
import com.wsdydeni.library_base.base.AppViewModel
import com.wsdydeni.library_base.base.BaseApplication
import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.network.observeState
import com.wsdydeni.module_sign.databinding.ActivityLoginBinding

@Route(path = "/sign/LoginActivity")
class LoginActivity : BaseVMActivity<ActivityLoginBinding,LoginViewModel>(R.layout.activity_login) {

    override fun initView() {
        window?.statusBarColor = resources.getColor(android.R.color.white,null)
    }

    override fun initData() {}

    override fun startObserve() {
        mViewModel.user.observeState(this, onLoading = {
            Toast.makeText(this, "正在登陆中", Toast.LENGTH_SHORT).show()
        }, onError = {
            Toast.makeText(this, it?.message ?: "登录失败", Toast.LENGTH_SHORT).show()
        },onSuccess = { apiResponse ->
            apiResponse?.data?.let {
                BaseApplication.mmkv?.encode(Config.LOGIN_USER,it) ?: throw NullPointerException("MMKV Not initialized")
                BaseApplication.mmkv?.encode(Config.LOGIN_STATE,true) ?: throw NullPointerException("MMKV Not initialized")
                AppViewModel.loginState.value = true
                finish()
            }
        })
    }

    override fun initViewModel(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun initViewModelId(): Int = BR.viewModel

}
