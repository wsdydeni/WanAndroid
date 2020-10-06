package com.wsdydeni.module_main.ui.setting

import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.library_base.base.AppViewModel
import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.config.PathConfig
import com.wsdydeni.library_base.network.NetworkApiService
import com.wsdydeni.library_base.utils.StatusUtil
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import kotlinx.android.synthetic.main.activity_settings.*

@Route(path = PathConfig.PATH_SETTING)
class SettingsActivity : BaseVMActivity() {

    private val viewModel by lazy { SettingViewModel() }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_settings,BR.viewModel,viewModel)
    }

    override fun initView() {
        StatusUtil.setStatusBarPaddingAndHeight(setting_toolbar,this)
        logout_btn.setOnClickListener {
            viewModel.logout()
        }
        setting_toolbar.setNavigationOnClickListener { finish() }
    }

    override fun initData() {}

    override fun startObserve() {
        viewModel.isLogoutSuccess.observe(this, {
            it?.let {
                if (it) {
                    NetworkApiService.clearCookie()
                    AppViewModel.loginState.value = false
                    ARouter.getInstance().build(PathConfig.PATH_LOGIN).navigation()
                    finish()
                } else {
                    Toast.makeText(this, "退出登录失败", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}



