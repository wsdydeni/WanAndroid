package com.wsdydeni.library_base.base

import android.app.Application
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.mmkv.MMKV
import com.wsdydeni.library_base.BuildConfig

open class BaseApplication : Application() {

    companion object {

        @JvmStatic private var mApplication: Application? = null

        @JvmStatic @Synchronized
        fun setApplication(application: Application) {
            mApplication = application
            MMKV.initialize(mApplication)
            ARouter.init(mApplication)
        }

        @JvmStatic val mmkv: MMKV? by lazy { MMKV.defaultMMKV() }

        @JvmStatic fun getApplication() = mApplication ?: throw NullPointerException("Application Not initialized")
    }

    override fun onCreate() {
        super.onCreate()
        setApplication(this)
        if(BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        Log.e("BaseApplication","onCreate")
    }

}