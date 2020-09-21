package com.wsdydeni.module_search

import androidx.recyclerview.widget.LinearLayoutManager
import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.network.observeState
import com.wsdydeni.library_view.multiTypeAdapter.MultiTypeAdapter
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder
import com.wsdydeni.library_view.multiTypeAdapter.createMultiTypeAdapter
import com.wsdydeni.module_search.binder.HotBinder
import com.wsdydeni.module_search.databinding.ActivitySearchBinding

class SearchActivity : BaseVMActivity<ActivitySearchBinding,SearchViewModel>(R.layout.activity_search) {

    private lateinit var recyclerAdapter: MultiTypeAdapter

    private val binderList = arrayListOf<MultiTypeBinder<*>>()

    private lateinit var hotBinder: HotBinder

    override fun initView() {
        window?.statusBarColor = resources.getColor(android.R.color.white,null)
        recyclerAdapter = createMultiTypeAdapter(mBinding.searchRecycler,LinearLayoutManager(this))
    }

    override fun initData() {
        hotBinder = HotBinder()
        binderList.add(hotBinder)
        mViewModel.getHotSearch()
    }

    override fun startObserve() {
        mViewModel.hotSearchItems.observeState(this,onSuccess = { apiResponse ->
            apiResponse?.data?.let {
                hotBinder.setHotData(it)
                recyclerAdapter.notifyAdapterChanged(binderList)
            }
        })
    }

    override fun initViewModel(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun initViewModelId(): Int = BR.viewModel


}
