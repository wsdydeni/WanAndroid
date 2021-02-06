package com.wsdydeni.module_main.ui.collect

import android.widget.Toast
import androidx.core.content.ContextCompat
import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.utils.StatusUtil
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import kotlinx.android.synthetic.main.activity_collect.*

class CollectActivity : BaseVMActivity() {

    private var curPage = 0

    private var isRefresh = false

    private var isLoadMore = false

    private val viewModel by lazy { CollectViewModel() }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_collect,BR.viewModel,viewModel)
    }

    override fun initView() {
        StatusUtil.setStatusBarPaddingAndHeight(collect_toolbar,this)
        collect_toolbar.setText("收藏的文章")
        collect_toolbar.setNavigationDrawable(ContextCompat.getDrawable(this,R.drawable.ic_detail_left_arrow)!!)
        collect_toolbar.setOnClickListener { finish() }
        collect_refresh.setOnRefreshListener {
            if(!isRefresh) {
                isRefresh = true
                loadMore()
            }
        }
        collect_refresh.setOnLoadMoreListener {
            if(!isLoadMore) {
                isLoadMore = true
                loadMore()
            }
        }
    }

    private fun loadMore() {
        viewModel.getCollectArticleList(curPage)
    }

    override fun initData() {
        collect_refresh.autoRefresh()
    }

    override fun startObserve() {
        viewModel.collectList.observe(this) {
            if(isRefresh) {
                collect_refresh.finishRefresh()
                if (it.collects.isEmpty()) {
                    Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show()
                }
                isRefresh = false
                curPage++
            }
            if(isLoadMore) {
                collect_refresh.finishLoadMore()
                if (it.collects.isEmpty()) {
                    Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show()
                }
                isLoadMore = false
                curPage++
            }
        }
    }
}