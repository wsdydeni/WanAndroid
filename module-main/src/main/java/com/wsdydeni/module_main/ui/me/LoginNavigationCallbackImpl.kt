package com.wsdydeni.module_main.ui.me

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.library_base.config.PathConfig


class LoginNavigationCallbackImpl : NavigationCallback {

    override fun onFound(postcard: Postcard?) {}
    override fun onLost(postcard: Postcard?) {}
    override fun onArrival(postcard: Postcard?) {}

    override fun onInterrupt(postcard: Postcard) {
        /*
            可以将路由参数传递给登录页面 成功登录直接跳转 懒得写了
         */
        val bundle = postcard.extras
        ARouter.getInstance().build(PathConfig.PATH_LOGIN)
            .with(bundle).navigation()
    }

}