package com.wsdydeni.module_main.ui.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.wsdydeni.library_base.base.AppViewModel
import com.wsdydeni.library_base.base.BaseApplication
import com.wsdydeni.library_base.config.Config

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppViewModel.loginState.value =  BaseApplication.mmkv?.decodeBool(Config.LOGIN_STATE) ?: false
        when(BaseApplication.mmkv?.decodeInt(Config.MODE_STATE,1)) {
            0 ->  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            1 ->  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        },500L)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {} // 夜间模式未启用，使用浅色主题
            Configuration.UI_MODE_NIGHT_YES -> {} // 夜间模式启用，使用深色主题
        }
    }
}