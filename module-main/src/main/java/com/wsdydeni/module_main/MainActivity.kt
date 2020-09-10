package com.wsdydeni.module_main

import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.base.BaseViewModel
import com.wsdydeni.module_main.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseVMActivity<ActivityMainBinding,BaseViewModel>(R.layout.activity_main) {

    override fun initView() {
        main_viewpager.adapter = MainViewPagerAdapter(this)
        main_viewpager.isUserInputEnabled = false
        main_viewpager.offscreenPageLimit = 3
        main_bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> main_viewpager.setCurrentItem(HOME_PAGE_INDEX,false)
                R.id.page_2 -> main_viewpager.setCurrentItem(BACKGROUND_PAGE_INDEX,false)
                R.id.page_3 -> main_viewpager.setCurrentItem(ME_PAGE_INDEX,false)
            }
            true
        }
        val colorStateList = resources.getColorStateList(R.color.colorPrimary,null)
        main_bottom_navigation.itemTextColor = colorStateList
        main_bottom_navigation.itemIconTintList = colorStateList
    }

    override fun initData() {}

    override fun startObserve() {}

    override fun initViewModel(): Class<BaseViewModel> = BaseViewModel::class.java

    override fun initViewModelId(): Int = BR.viewModel

}