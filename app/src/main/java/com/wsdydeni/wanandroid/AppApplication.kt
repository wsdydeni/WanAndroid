package com.wsdydeni.wanandroid

import android.app.Application
import android.util.Log

open class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.e("AppApplication","create")
    }
}