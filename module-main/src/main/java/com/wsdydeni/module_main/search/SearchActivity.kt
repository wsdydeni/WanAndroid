package com.wsdydeni.module_main.search

import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.network.observeState
import com.wsdydeni.library_base.utils.SoftKeyboardUtil
import com.wsdydeni.library_view.multiTypeAdapter.MultiTypeAdapter
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder
import com.wsdydeni.library_view.multiTypeAdapter.createMultiTypeAdapter
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.binder.HotBinder
import com.wsdydeni.module_main.databinding.ActivitySearchBinding
import kotlinx.android.synthetic.main.activity_search.*

@Route(path = "/main/SearchActivity")
class SearchActivity : BaseVMActivity<ActivitySearchBinding,SearchViewModel>(R.layout.activity_search) {

    private lateinit var recyclerAdapter: MultiTypeAdapter

    private lateinit var text : String

    private val binderList = arrayListOf<MultiTypeBinder<*>>()

    private lateinit var hotBinder: HotBinder

    override fun initView() {
        recyclerAdapter = createMultiTypeAdapter(mBinding.searchRecycler,LinearLayoutManager(this))
        search_toolbar.setNavigationOnClickListener { onBackPressed() }
        search_edit.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                SoftKeyboardUtil.hideSoftKeyboard(this)
                text = if(search_edit.text.isNullOrEmpty()) {
                    search_edit.hint.toString()
                }else {
                    search_edit.text.toString()
                }
                ARouter.getInstance().build("/main/DetailActivity").withString("text",text).navigation()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    override fun initData() {
        hotBinder = HotBinder().apply {
            setOnClick {
                ARouter.getInstance().build("/main/DetailActivity").withString("text",it).navigation()
            }
        }
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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun initViewModel(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun initViewModelId(): Int = BR.viewModel

}
