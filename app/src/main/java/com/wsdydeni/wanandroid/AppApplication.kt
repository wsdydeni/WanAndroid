package com.wsdydeni.wanandroid

import android.app.Application
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter

open class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}