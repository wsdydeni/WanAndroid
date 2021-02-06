package com.wsdydeni.module_main.ui.background

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.config.PathConfig
import com.wsdydeni.library_base.utils.StatusUtil
import com.wsdydeni.library_view.recyclerview.SpaceItemDecoration
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.ui.adpater.SearchAdapter
import kotlinx.android.synthetic.main.activity_system.*

@Route(path = PathConfig.PATH_SYSTEM_DETAIL)
open class SystemActivity : BaseVMActivity() {

    @JvmField
    @Autowired(name = "cid")
    var cid : Int = 0

    @JvmField
    @Autowired(name = "name")
    var name : String = ""

    private lateinit var viewModel: BackgroundViewModel

    private val listArticleAdapter by lazy { SearchAdapter() }

    private var isRefresh = false

    private var isLoadMore = false

    private var curProjectPage = 0

    override fun getDataBindingConfig(): DataBindingConfig {
        viewModel = BackgroundFragment.backgroundViewModel
        return DataBindingConfig(R.layout.activity_system,BR.viewModel,viewModel)
    }

    override fun initView() {
        StatusUtil.setStatusBarPaddingAndHeight(system_toolbar,this)
        system_toolbar.setNavigationDrawable(ContextCompat.getDrawable(this,R.drawable.ic_detail_left_arrow)!!)
        system_toolbar.setOnClickListener { finish() }
        system_toolbar.setText(name)
        system_refresh.setOnRefreshListener {
            if(!isRefresh) {
                isRefresh = true
                loadMore()
            }
        }
        system_refresh.setOnLoadMoreListener {
            if(!isLoadMore) {
                isLoadMore = true
                loadMore()
            }
        }
        system_recycler.adapter = listArticleAdapter
        system_recycler.layoutManager = LinearLayoutManager(this)
        system_recycler.addItemDecoration(SpaceItemDecoration(20))
    }

    private fun loadMore() {
        viewModel.getListArticle(cid,curProjectPage)
    }

    override fun initData() {
        system_refresh.autoRefresh()
    }

    override fun startObserve() {
        viewModel.listArticles.observe(this) { articleList ->
            articleList.datas.let {
                if (isRefresh) {
                    system_refresh.finishRefresh()
                    if (it.size == 0) {
                        Toast.makeText(this, "没有新数据了", Toast.LENGTH_SHORT).show()
                    } else {
                        listArticleAdapter.setData(it, true)
                    }
                    isRefresh = false
                    curProjectPage++
                }
                if (isLoadMore) {
                    system_refresh.finishLoadMore()
                    if (it.size == 0) {
                        Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show()
                    } else {
                        listArticleAdapter.setData(it)
                    }
                    isLoadMore = false
                    curProjectPage++
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}