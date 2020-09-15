package com.wsdydeni.library_base.util

import android.R
import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity

object StatusBarUtil {

    fun initStatusBar(activity: FragmentActivity) {
        if (!setStatusBarDarkTheme(activity, true)) {
            setStatusBarColor(activity, Color.parseColor("#EFEFEF"))
        }
    }

    fun setStatusBarColor(activity: Activity, colorId: Int) {
        val window = activity.window
        window.statusBarColor = colorId
    }

    fun setTranslucentStatus(activity: Activity) {
        val window = activity.window
        val decorView = window.decorView
        val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.systemUiVisibility = option
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    }

    fun setRootViewFitsSystemWindows(activity: Activity, fitSystemWindows: Boolean) {
        val winContent = activity.findViewById<ViewGroup>(R.id.content)
        if (winContent.childCount > 0) {
            val rootView = winContent.getChildAt(0) as ViewGroup
            rootView.fitsSystemWindows = fitSystemWindows
        }
    }

    fun setStatusBarDarkTheme(activity: Activity, dark: Boolean): Boolean {
        setStatusBarFontIconDark(activity, dark)
        return true
    }

    private fun setStatusBarFontIconDark(activity: Activity, dark: Boolean) {
        setCommonUI(activity, dark)
    }

    private fun setCommonUI(activity: Activity, dark: Boolean) {
        val decorView = activity.window.decorView
        var vis = decorView.systemUiVisibility
        vis = if (dark) {
            vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        if (decorView.systemUiVisibility != vis) {
            decorView.systemUiVisibility = vis
        }
    }
}