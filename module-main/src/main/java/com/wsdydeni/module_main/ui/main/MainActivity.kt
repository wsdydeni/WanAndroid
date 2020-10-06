package com.wsdydeni.module_main.ui.main

import android.content.Context
import android.content.res.Configuration
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.airbnb.lottie.LottieDrawable
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.base.BaseViewModel
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.config.PathConfig
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.ui.adpater.MainViewPagerAdapter
import com.wsdydeni.module_main.ui.home.MainIS
import com.wsdydeni.module_main.view.getLottieDrawable
import com.wsdydeni.module_main.view.mNavigationAnimationList
import com.wsdydeni.module_main.view.mNavigationTitleList
import com.wsdydeni.module_main.view.setLottieDrawable
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = PathConfig.PATH_MAIN)
class MainActivity : BaseVMActivity(), MainIS, BottomNavigationView.OnNavigationItemReselectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private val baseViewModel by lazy { BaseViewModel() }

    private var mPreClickPosition: Int = 0

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_main,BR.viewModel,baseViewModel)
    }

    override fun initView() {
        bottomNavigationView = main_bottom_navigation
        main_viewpager.setScanScroll(false)
        main_viewpager.adapter = MainViewPagerAdapter(fragmentManager = supportFragmentManager)
        main_viewpager.offscreenPageLimit = 3
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        bottomNavigationView.menu.apply {
            for (i in 0 until mNavigationTitleList.size) {
                add(Menu.NONE, i, Menu.NONE, mNavigationTitleList[i])
            }
            setLottieDrawable(mNavigationAnimationList,bottomNavigationView)
        }
        bottomNavigationView.setOnNavigationItemReselectedListener(this)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.selectedItemId = 0
    }

    override fun setNavigationVisibility(visible: Boolean) {
        if(bottomNavigationView.isShown && !visible)  {
            bottomNavigationView.visibility = View.GONE
        }else if(!bottomNavigationView.isShown && visible) {
            bottomNavigationView.visibility = View.VISIBLE
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        handleNavigationItem(item)
//        startDeviceVibrate(this)
        when(item.itemId) {
            0 -> main_viewpager.setCurrentItem(0,false)
            1 -> main_viewpager.setCurrentItem(1,false)
            2 -> main_viewpager.setCurrentItem(2,false)
        }
        return true
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        handleNavigationItem(item)
    }

    private fun handleNavigationItem(item: MenuItem) {
        handlePlayLottieAnimation(item)
        mPreClickPosition = item.itemId
    }

    private fun handlePlayLottieAnimation(item: MenuItem) {
        val currentIcon = item.icon as? LottieDrawable
        currentIcon?.playAnimation()
        if (item.itemId != mPreClickPosition) {
            bottomNavigationView.menu.findItem(mPreClickPosition).icon = getLottieDrawable(mNavigationAnimationList[mPreClickPosition], bottomNavigationView)
        }
    }

    override fun init(context: Context?) {}

    override fun initData() {}

    override fun startObserve() {}

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {} // 夜间模式未启用，使用浅色主题
            Configuration.UI_MODE_NIGHT_YES -> {} // 夜间模式启用，使用深色主题
        }
    }
}