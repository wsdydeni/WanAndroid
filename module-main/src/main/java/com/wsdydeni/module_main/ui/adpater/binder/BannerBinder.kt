package com.wsdydeni.module_main.ui.adpater.binder

import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.library_view.banner.BannerAdapter
import com.wsdydeni.library_view.banner.BannerInfo
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.databinding.HomeItemBannerBinding

class BannerBinder(private val dataList: List<BannerInfo>) : MultiTypeBinder<HomeItemBannerBinding>() {

    private var bannerAdapter: BannerAdapter = BannerAdapter().apply {
        setOnClickListener {
            ARouter.getInstance().build("/browser/BrowserActivity").withString("url",it).navigation()
        }
    }

    fun setData(dataList: List<BannerInfo>) {
        bannerAdapter.setData(dataList)
    }

    fun startLoop() = binding?.homeBanner?.startLoop()

    fun stopLoop() = binding?.homeBanner?.stopLoop()

    override fun onBindViewHolder(binding: HomeItemBannerBinding) {
        binding.homeBanner.setAdapter(bannerAdapter).apply { setData(dataList) }
        binding.homeBanner.dismissIndicatorView()
    }

    override fun layoutId(): Int = R.layout.home_item_banner

    override fun areContentsTheSame(other: Any): Boolean = other is BannerBinder && other.dataList == dataList
}