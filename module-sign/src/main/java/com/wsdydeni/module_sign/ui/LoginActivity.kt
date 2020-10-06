package com.wsdydeni.module_sign.ui

import android.content.res.Configuration
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.wsdydeni.library_base.base.AppViewModel
import com.wsdydeni.library_base.base.BaseApplication
import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.config.Config
import com.wsdydeni.library_base.config.PathConfig
import com.wsdydeni.library_view.loadingdialog.LoadingDialog
import com.wsdydeni.library_view.loadingdialog.LoadingStatusListener
import com.wsdydeni.module_sign.BR
import com.wsdydeni.module_sign.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Route(path = PathConfig.PATH_LOGIN)
class LoginActivity : BaseVMActivity() {

    private val loginViewModel by lazy { LoginViewModel() }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_login, BR.viewModel,loginViewModel)
    }

    private lateinit var mHandler: Handler

    private var mSuccessRunnable = Runnable {
        BaseApplication.mmkv?.encode(Config.LOGIN_STATE, true) ?: throw NullPointerException("MMKV Not initialized")
        dialog?.close()
        AppViewModel.loginState.value = true
    }

    private var mFailureRunnable = Runnable {
        Toast.makeText(this@LoginActivity, "账号或者密码不正确", Toast.LENGTH_SHORT).show()
        dialog?.close()
    }

    private var dialog: LoadingDialog? = null

    @ExperimentalCoroutinesApi
    override fun initView() {
        mHandler = Handler(Looper.getMainLooper())
        login_btn.setOnClickListener {
            dialog = createNewDialog()
            loginViewModel.login()
        }
    }

    private fun createNewDialog(): LoadingDialog {
        return LoadingDialog(this).apply {
            setLoadingStatusListener(object : LoadingStatusListener {
                override fun onSuccess() {
                    mHandler.postDelayed(mSuccessRunnable,1000L)
                }
                override fun onFailure() {
                    mHandler.postDelayed(mFailureRunnable,500L)
                }
            })
            show("登陆中")
        }
    }

    override fun initData() {}


    override fun startObserve() {
        loginViewModel.user.observe(this,{
            it?.let {
                BaseApplication.mmkv?.encode(Config.LOGIN_USER,it) ?: throw NullPointerException("MMKV Not initialized")
                dialog?.success("登录成功,正在为您跳转")
            }
        })
        loginViewModel.loginFailure.observe(this,{
            it?.let { dialog?.failure("账号密码不匹配!") }
        })
        AppViewModel.loginState.observe(this,{
            if(it) finish()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.clear()
        mHandler.removeCallbacks(mFailureRunnable)
        mHandler.removeCallbacks(mSuccessRunnable)
    }

    /*
        数据要清空 重新请求
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {} // 夜间模式未启用，使用浅色主题
            Configuration.UI_MODE_NIGHT_YES -> {} // 夜间模式启用，使用深色主题
        }
    }

}
