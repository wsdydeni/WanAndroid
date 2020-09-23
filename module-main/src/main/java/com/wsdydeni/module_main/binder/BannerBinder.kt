package com.wsdydeni.module_main.binder

import com.google.android.material.snackbar.Snackbar
import com.wsdydeni.library_view.banner.Banner
import com.wsdydeni.library_view.banner.BannerAdapter
import com.wsdydeni.library_view.banner.BannerInfo
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.databinding.HomeItemBannerBinding

class BannerBinder(val list: List<BannerInfo>) : MultiTypeBinder<HomeItemBannerBinding>() {

    private lateinit var banner: Banner

    private var bannerAdapter: BannerAdapter = BannerAdapter().apply {
        setOnClickListener {
            Snackbar.make(binding!!.homeBanner,"点击位置$it",Snackbar.LENGTH_SHORT).show()
        }
    }

    fun startLoop() = banner.startLoop()

    fun stopLoop() = banner.stopLoop()

    fun isLooping() = banner.isLooping

    override fun layoutId(): Int = R.layout.home_item_banner

    override fun areContentsTheSame(other: Any): Boolean = other is BannerBinder && other.list == list

    override fun onBindViewHolder(binding: HomeItemBannerBinding) {
        banner = binding.homeBanner
        banner.setAdapter(bannerAdapter).apply { setData(list) }
        banner.dismissIndicatorView()
    }
}