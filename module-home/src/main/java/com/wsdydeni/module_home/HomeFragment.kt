package com.wsdydeni.module_home

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.library_base.network.observeState
import com.wsdydeni.library_view.multiTypeAdapter.MultiTypeAdapter
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder
import com.wsdydeni.library_view.multiTypeAdapter.createMultiTypeAdapter
import com.wsdydeni.module_home.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(R.layout.fragment_home) {

    private lateinit var recyclerAdapter: MultiTypeAdapter

    override fun initView() {
        recyclerAdapter = createMultiTypeAdapter(mBinding.homeRecycler,LinearLayoutManager(context))
        home_recycler.adapter = recyclerAdapter
    }

    override fun initData() {
        recyclerAdapter.notifyAdapterChanged(mutableListOf<MultiTypeBinder<*>>().apply {

        })
        mViewModel.getBanner()
    }

    override fun startObserve() {
        mViewModel.banner.observeState(this) {
            onLoading = {
                Log.e("HomeFragment","onLoading")
            }

            onSuccess = {
                Log.e("HomeFragment","onSuccess")
            }

            onError = {
                Log.e("HomeFragment","onError")
            }

            onEmpty = {
                Log.e("HomeFragment","onEmpty")
            }
        }
    }

    override fun initViewModel(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun initViewModelId(): Int = BR.viewModel

}