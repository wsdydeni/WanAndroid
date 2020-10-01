package com.wsdydeni.module_sign.ui

import android.content.res.Configuration
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.wsdydeni.library_base.Config
import com.wsdydeni.library_base.base.*
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.module_sign.BR
import com.wsdydeni.module_sign.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Route(path = "/sign/LoginActivity")
class LoginActivity : BaseVMActivity() {

    private val loginViewModel by lazy { LoginViewModel() }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_login, BR.viewModel,loginViewModel)
    }

    @ExperimentalCoroutinesApi
    override fun initView() {
        login_btn.setOnClickListener {
            loginViewModel.flowRequest(
                suspend { loginViewModel.repository.login(
                    loginViewModel.userName.get() ?: "",
                        loginViewModel.passWord.get() ?: ""
                    )
                },loginViewModel.user1,false)
        }
    }

    override fun initData() {}

    override fun startObserve() {
        loginViewModel.user1.observeState(this, onLoading = {
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {} // 夜间模式未启用，使用浅色主题
            Configuration.UI_MODE_NIGHT_YES -> {} // 夜间模式启用，使用深色主题
        }
    }

}
