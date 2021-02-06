package com.wsdydeni.module_main.ui.search

import android.content.res.Configuration
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.config.PathConfig
import com.wsdydeni.library_base.utils.SoftKeyboardUtil
import com.wsdydeni.library_base.utils.StatusUtil
import com.wsdydeni.library_view.recyclerview.SpaceItemDecoration
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.ui.adpater.SearchAdapter
import kotlinx.android.synthetic.main.activity_detail.*

@Route(path = PathConfig.PATH_SEARCH_DETAIL)
class DetailActivity : BaseVMActivity(){

    @Autowired
    lateinit var text : String

    private var isRefresh = false

    private var isLoadMore = false

    private var curPage = 0

    private val searchViewModel by lazy { SearchViewModel() }

    private var searchAdapter = SearchAdapter()

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_detail,BR.viewModel,searchViewModel)
    }

    override fun initView() {
        StatusUtil.setStatusBarPaddingAndHeight(detail_toolbar,this)
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
                    text = input
                    curPage = 0
                    searchAdapter.clearData()
                }
                searchViewModel.getSearchItem(text,curPage)
                return@setOnEditorActionListener true
            }
            false
        }
        detail_recycler.adapter = searchAdapter
        detail_recycler.layoutManager = LinearLayoutManager(this)
        detail_recycler.addItemDecoration(SpaceItemDecoration(20))
        detail_refreshLayout.setOnRefreshListener {
            if (!isRefresh) {
                isRefresh = true
                loadMore()
            }
        }
        detail_refreshLayout.setOnLoadMoreListener {
           if(!isLoadMore) {
               isLoadMore = true
               loadMore()
           }
        }
    }

    private fun loadMore() {
        searchViewModel.getSearchItem(text,curPage)
    }

    override fun initData() {
        detail_edit.setText(text,TextView.BufferType.EDITABLE)
        detail_refreshLayout.autoRefresh()
    }

    override fun startObserve() {
        searchViewModel.searchItems.observe(this,{ articleList ->
            articleList.datas.let {
                if(isRefresh) {
                    detail_refreshLayout.finishRefresh()
                    if (it.size == 0) {
                        Toast.makeText(this, "没有新数据了", Toast.LENGTH_SHORT).show()
                    }else {
                        searchAdapter.setData(it,true)
                    }
                    isRefresh = false
                    curPage++
                }
                if(isLoadMore) {
                    detail_refreshLayout.finishLoadMore()
                    if (it.size == 0) {
                        Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show()
                    }else {
                        searchAdapter.setData(it)
                    }
                    isLoadMore = false
                    curPage++
                }
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {} // 夜间模式未启用，使用浅色主题
            Configuration.UI_MODE_NIGHT_YES -> {} // 夜间模式启用，使用深色主题
        }
    }
}