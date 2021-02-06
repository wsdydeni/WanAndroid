package com.wsdydeni.module_main.ui.home

import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayout
import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.config.PathConfig
import com.wsdydeni.library_base.utils.StatusUtil
import com.wsdydeni.library_view.banner.BannerAdapter
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.ui.adpater.HomeViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*


@Route(path = PathConfig.PATH_HOME)
class HomeFragment : BaseFragment() {

    companion object {
        val homeViewModel = HomeViewModel()
    }

    private var bannerAdapter: BannerAdapter = BannerAdapter().apply {
        setOnClickListener {
            ARouter.getInstance().build(PathConfig.PATH_BROWSER).withString("url",it).navigation()
        }
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_home,BR.viewModel,homeViewModel)
    }

    override fun initView() {
        StatusUtil.setStatusBarPaddingAndHeight(home_toolbar,this.activity)
        val adapter = HomeViewPagerAdapter(childFragmentManager)
        home_viewpager.adapter = adapter
        home_viewpager.offscreenPageLimit = 2
        home_viewpager.addOnPageChangeListener(object : TabLayout.TabLayoutOnPageChangeListener(home_tab){})
        home_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
            override fun onTabSelected(tab: TabLayout.Tab) {
                home_viewpager.setCurrentItem(tab.position,false)
            }
        })
        home_toolbar.setTextSize(18f)
        home_toolbar.setMenuDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_search)!!)
        home_toolbar.setOnClickListener {
            ARouter.getInstance().build(PathConfig.PATH_SEARCH).navigation()
        }
        home_toolbar.setText("欢迎来到玩WanAndroid")
        home_banner.dismissIndicatorView()
        home_fab.setOnClickListener {
            ((childFragmentManager.fragments[home_viewpager.currentItem]) as HomeIS).scrollToTop()
        }
    }

    override fun initData() {
        homeViewModel.getBannerList()
    }

    override fun startObserve() {
        homeViewModel.bannerList.observe(this,{
            home_banner.setAdapter(bannerAdapter).setData(it)
        })
    }

    override fun onPause() {
        super.onPause()
        home_banner?.stopLoop()
    }

    override fun onResume() {
        super.onResume()
        home_banner?.startLoop()
    }

    override fun onDestroy() {
        home_banner?.stopLoop()
        super.onDestroy()
    }
}