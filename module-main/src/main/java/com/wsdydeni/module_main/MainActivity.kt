package com.wsdydeni.module_main

import android.content.Context
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.base.BaseViewModel
import com.wsdydeni.module_home.ISkill
import com.wsdydeni.module_main.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = "/app/MainActivity")
class MainActivity : BaseVMActivity<ActivityMainBinding,BaseViewModel>(R.layout.activity_main),ISkill{

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun initView() {
        bottomNavigationView = mBinding.mainBottomNavigation
        main_viewpager.adapter = MainViewPagerAdapter(this)
        main_viewpager.isUserInputEnabled = false
        main_viewpager.offscreenPageLimit = 3
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> main_viewpager.setCurrentItem(HOME_PAGE_INDEX,false)
                R.id.page_2 -> main_viewpager.setCurrentItem(BACKGROUND_PAGE_INDEX,false)
                R.id.page_3 -> main_viewpager.setCurrentItem(ME_PAGE_INDEX,false)
            }
            true
        }
        val colorStateList = resources.getColorStateList(R.color.selector_color,null)
        bottomNavigationView.itemTextColor = colorStateList
        bottomNavigationView.itemIconTintList = colorStateList
    }

    override fun setNavigationVisibility(visible: Boolean) {
        if(bottomNavigationView.isShown && !visible)  {
            bottomNavigationView.visibility = View.GONE
        }else if(!bottomNavigationView.isShown && visible) {
            bottomNavigationView.visibility = View.VISIBLE
        }
    }

    override fun init(context: Context?) {}

    override fun initData() {}

    override fun startObserve() {}

    override fun initViewModel(): Class<BaseViewModel> = BaseViewModel::class.java

    override fun initViewModelId(): Int = BR.viewModel

}