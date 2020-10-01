package com.wsdydeni.module_main.ui.search

import android.content.res.Configuration
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.utils.SoftKeyboardUtil
import com.wsdydeni.library_base.utils.StatusUtil
import com.wsdydeni.library_view.SpaceItemDecoration
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.ui.adpater.SearchAdapter
import kotlinx.android.synthetic.main.activity_detail.*

@Route(path = "/main/DetailActivity")
class DetailActivity : BaseVMActivity(){

    @Autowired
    lateinit var text : String

    private var isRefresh = false

    private var isLoadMore = false

    private var curPage = 0

    private val searchViewModel by lazy { SearchViewModel() }

    private var searchAdapter = SearchAdapter().apply {
        setOnClickListener {
            ARouter.getInstance().build("/browser/BrowserActivity").withString("url",it).navigation()
        }
    }

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
                loadMore()
                isRefresh = true
            }
        }
        detail_refreshLayout.setOnLoadMoreListener {
           if(!isLoadMore) {
               loadMore()
               isLoadMore = true
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
        searchViewModel.searchItems.observe(this,{
            if(isRefresh) {
                detail_refreshLayout.finishRefresh()
                searchAdapter.setData(it.datas,true)
                isRefresh = false
                curPage = 0
            }
            if(isLoadMore) {
                detail_refreshLayout.finishLoadMore()
                searchAdapter.setData(it.datas)
                isLoadMore = false
                curPage++
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