package com.wsdydeni.module_main.ui.home

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayout
import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.utils.StatusUtil
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.ui.adpater.HomeViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*

// 改成普通懒加载Fragment

@Route(path = "/main/HomeFragment")
class HomeFragment : BaseFragment() {

    companion object {
        val homeViewModel = HomeViewModel()
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_home,BR.viewModel,homeViewModel)
    }

    override fun initView() {
        StatusUtil.setStatusBarPaddingAndHeight(home_toolbar,this.activity)
        home_viewpager.adapter = HomeViewPagerAdapter(childFragmentManager)
        home_viewpager.offscreenPageLimit = 2
        home_viewpager.addOnPageChangeListener(object : TabLayout.TabLayoutOnPageChangeListener(home_tab){})
        home_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
            override fun onTabSelected(tab: TabLayout.Tab) {
                home_viewpager.setCurrentItem(tab.position,false)
            }
        })
        home_toolbar.setOnMenuItemClickListener {
            if(it.itemId == R.id.go_search) {
                ARouter.getInstance().build("/main/SearchActivity").navigation()
            }
            true
        }
    }

    override fun initData() {}

    override fun startObserve() {
        homeViewModel.tabVisible.observe(this,{
            // toolbar 跟随滑动隐藏
        })
    }
}