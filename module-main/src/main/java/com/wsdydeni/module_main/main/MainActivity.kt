package com.wsdydeni.module_main.main

import android.content.Context
import android.graphics.Color
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.airbnb.lottie.LottieDrawable
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.base.BaseViewModel
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.databinding.ActivityMainBinding
import com.wsdydeni.module_main.home.ISkill
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = "/main/MainActivity")
class MainActivity : BaseVMActivity<ActivityMainBinding,BaseViewModel>(R.layout.activity_main), ISkill,
    BottomNavigationView.OnNavigationItemReselectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private var mPreClickPosition: Int = 0

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun initView() {
        bottomNavigationView = mBinding.mainBottomNavigation
        main_viewpager.adapter = MainViewPagerAdapter(this)
        main_viewpager.isUserInputEnabled = false
        main_viewpager.offscreenPageLimit = 3
        initBottomNavigationView()
        initStatusBar()
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
        startDeviceVibrate(this)
        when(item.itemId) {
            0 -> main_viewpager.setCurrentItem(HOME_PAGE_INDEX,false)
            1 -> main_viewpager.setCurrentItem(BACKGROUND_PAGE_INDEX,false)
            2 -> main_viewpager.setCurrentItem(ME_PAGE_INDEX,false)
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

    private fun initStatusBar() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun init(context: Context?) {}

    override fun initData() {}

    override fun startObserve() {}

    override fun initViewModel(): Class<BaseViewModel> = BaseViewModel::class.java

    override fun initViewModelId(): Int = BR.viewModel

}