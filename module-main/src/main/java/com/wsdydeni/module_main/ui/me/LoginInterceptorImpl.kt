package com.wsdydeni.module_main.ui.me

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.wsdydeni.library_base.base.AppViewModel
import com.wsdydeni.library_base.config.PathConfig

@Interceptor(name = "LOGIN",priority = 3)
class LoginInterceptorImpl : IInterceptor {

    override fun init(context: Context?) {}

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        val path = postcard.path
        val isLogin = AppViewModel.loginState.value ?: false
        if(!isLogin) {
            when(path) {
                PathConfig.PATH_SETTING,PathConfig.PATH_DARK_MODE -> {
                    callback.onInterrupt(null)
                }

                else -> callback.onContinue(postcard)
            }
        }else {
            callback.onContinue(postcard)
        }
    }
}