package com.wsdydeni.module_home.banner

import com.google.android.material.snackbar.Snackbar
import com.wsdydeni.library_view.banner.BannerAdapter
import com.wsdydeni.library_view.banner.BannerInfo
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder
import com.wsdydeni.module_home.R
import com.wsdydeni.module_home.databinding.HomeItemBannerBinding

class BannerBinder(val list: List<BannerInfo>) : MultiTypeBinder<HomeItemBannerBinding>() {

    private var bannerAdapter: BannerAdapter = BannerAdapter().apply {
        setOnClickListener {
            Snackbar.make(binding!!.homeBanner,"点击位置$it",Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun layoutId(): Int = R.layout.home_item_banner

    override fun areContentsTheSame(other: Any): Boolean = other is BannerBinder && other.list == list

    override fun onBindViewHolder(binding: HomeItemBannerBinding) {
        val banner = binding.homeBanner
        banner.setAdapter(bannerAdapter).apply { setData(list) }
        banner.dismissIndicatorView()
    }
}