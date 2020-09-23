package com.wsdydeni.module_main.search

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.network.observeState
import com.wsdydeni.library_base.utils.SoftKeyboardUtil
import com.wsdydeni.library_view.SpaceItemDecoration
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.binder.adapter.SearchAdapter
import com.wsdydeni.module_main.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*

@Route(path = "/main/DetailActivity")
class DetailActivity : BaseVMActivity<ActivityDetailBinding,SearchViewModel>(R.layout.activity_detail){

    @Autowired
    lateinit var text : String

    private var curPage = 0

    private var isRefresh = false

    private var searchAdapter = SearchAdapter().apply {
        setOnClickListener {
            ARouter.getInstance().build("/browser/BrowserActivity").withString("url",it).navigation()
        }
    }

    override fun initView() {
        detail_toolbar.setNavigationOnClickListener { onBackPressed() }
        detail_edit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                SoftKeyboardUtil.hideSoftKeyboard(this)
                val input = if(detail_edit.text.isNullOrEmpty()) {
                    detail_edit.hint.toString()
                }else {
                    detail_edit.text.toString()
                }
                if(input != text) {
                    curPage = 0
                    text = input
                    searchAdapter.clearData()
                }
                mViewModel.search(curPage,text)
                return@setOnEditorActionListener true
            }
            false
        }
        detail_recycler.adapter = searchAdapter
        detail_recycler.layoutManager = LinearLayoutManager(this)
        detail_recycler.addItemDecoration(SpaceItemDecoration(20))
        detail_refreshLayout.setOnRefreshListener {
            if (!isRefresh) {
                searchAdapter.clearData()
                loadMore()
                isRefresh = detail_refreshLayout.isRefreshing
            }
        }
        detail_refreshLayout.setOnLoadMoreListener {
            loadMore()
        }
    }

    private fun loadMore() {
        curPage++
        mViewModel.search(curPage,text)
    }

    override fun initData() {
        detail_edit.setText(text,TextView.BufferType.EDITABLE)
        mViewModel.search(curPage,text)
    }

    override fun startObserve() {
        mViewModel.searchItems.observeState(this,onError = {
            if(isRefresh) {
                detail_refreshLayout.finishRefresh(false)
                isRefresh = false
            }else {
                detail_refreshLayout.finishLoadMore(false)
            }
        },onSuccess = { apiResponse ->
            if(isRefresh) {
                isRefresh = false
                detail_refreshLayout.finishRefresh()
            }else {
                detail_refreshLayout.finishLoadMore()
            }
            apiResponse?.data?.datas?.let {
                if(it.isEmpty()) {
                    // 显示空布局
                }else {
                    searchAdapter.setData(it)
                }
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