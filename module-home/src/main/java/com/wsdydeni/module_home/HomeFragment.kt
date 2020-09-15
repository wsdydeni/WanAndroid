package com.wsdydeni.module_home

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.library_base.network.observeState
import com.wsdydeni.library_view.multiTypeAdapter.MultiTypeAdapter
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder
import com.wsdydeni.library_view.multiTypeAdapter.createMultiTypeAdapter
import com.wsdydeni.module_home.article.Article
import com.wsdydeni.module_home.article.ArticleBinder
import com.wsdydeni.module_home.banner.BannerBinder
import com.wsdydeni.module_home.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(R.layout.fragment_home) {

    private lateinit var recyclerAdapter: MultiTypeAdapter

    override fun initView() {
        recyclerAdapter = createMultiTypeAdapter(mBinding.homeRecycler,LinearLayoutManager(context))
    }

    override fun initData() {
        mViewModel.getBanner()
        mViewModel.getTopArticles()
    }

    override fun startObserve() {
        val binderList = mutableListOf<MultiTypeBinder<*>>()
        mViewModel.banner.observeState(this,onSuccess = {
            it?.let {
                binderList.add(BannerBinder(it.data))
                recyclerAdapter.notifyAdapterChanged(binderList)
            }
        })
        mViewModel.topArticles.observeState(this,onSuccess = {
            it?.let {
                binderList.add(ArticleBinder(it.data as ArrayList<Article>))
                Log.e("startObserve","binderList size: ${binderList.size}")
                recyclerAdapter.notifyAdapterChanged(binderList)
            }
        })
    }

    override fun initViewModel(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun initViewModelId(): Int = BR.viewModel

}