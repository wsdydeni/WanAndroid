package com.wsdydeni.module_home.banner

import com.google.android.material.snackbar.Snackbar
import com.wsdydeni.library_view.banner.BannerAdapter
import com.wsdydeni.library_view.banner.BannerInfo
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder
import com.wsdydeni.module_home.R
import com.wsdydeni.module_home.databinding.HomeItemBannerBinding

class BannerBinder(val list: List<BannerInfo>) : MultiTypeBinder<HomeItemBannerBinding>() {

    private lateinit var bannerAdapter: BannerAdapter

    override fun layoutId(): Int = R.layout.home_item_banner

    override fun areContentsTheSame(other: Any): Boolean = other is BannerBinder && other.list == list

    override fun onBindViewHolder(binding: HomeItemBannerBinding) {
        bannerAdapter = BannerAdapter().apply {
            setOnClickListener {
                Snackbar.make(binding.homeBanner,"点击位置$it",Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.homeBanner.setAdapter(bannerAdapter)
        bannerAdapter.setData(list)
    }
}