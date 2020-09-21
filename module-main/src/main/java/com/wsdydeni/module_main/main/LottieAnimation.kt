package com.wsdydeni.module_main.main

import android.app.Service
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.Menu
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView

// 鸣谢：静心Study
// 链接：https://juejin.im/post/6867895624025997320

enum class LottieAnimation(val value: String) {
    HOME("lottie/bottom_tab_home.json"),
    BACKGROUND("lottie/bottom_tab_background.json"),
    MINE("lottie/bottom_tab_mine.json"),
}

val mNavigationAnimationList = arrayListOf(
    LottieAnimation.HOME,
    LottieAnimation.BACKGROUND,
    LottieAnimation.MINE,
)

val mNavigationTitleList = arrayListOf<String>().apply {
    add("首页")
    add("广场")
    add("个人")
}

fun getLottieDrawable(animation: LottieAnimation, bottomNavigationView: BottomNavigationView): LottieDrawable {
    return LottieDrawable().apply {
        val result = LottieCompositionFactory.fromAssetSync(bottomNavigationView.context.applicationContext, animation.value)
        callback = bottomNavigationView
        composition = result.value
    }
}

fun Menu.setLottieDrawable(lottieAnimationList: ArrayList<LottieAnimation>,navigationView: BottomNavigationView) {
    for (i in 0 until mNavigationTitleList.size) {
        findItem(i)?.icon = getLottieDrawable(lottieAnimationList[i], navigationView)
    }
}

fun startDeviceVibrate(context: Context) {
    val vibrator = context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
    if (vibrator.hasVibrator()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
        }
    }
}

