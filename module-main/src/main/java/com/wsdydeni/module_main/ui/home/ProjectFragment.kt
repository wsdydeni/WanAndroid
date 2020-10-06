package com.wsdydeni.module_main.ui.home

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_view.recyclerview.SpaceItemDecoration
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.ui.adpater.ProjectAdapter
import kotlinx.android.synthetic.main.fragment_project.*

class ProjectFragment : BaseFragment(),HomeIS {

    private var isRefresh = false

    private var isLoadMore = false

    private var curProjectPage = 0

    private lateinit var homeViewModel : HomeViewModel

    private val projectNewAdapter = ProjectAdapter()

    override fun getDataBindingConfig(): DataBindingConfig {
        homeViewModel = HomeFragment.homeViewModel
        return DataBindingConfig(R.layout.fragment_project,BR.viewModel,homeViewModel)
    }

    override fun initView() {
        val layoutManager = LinearLayoutManager(this.activity)
        project_recycler.layoutManager = layoutManager
        project_recycler.setItemViewCacheSize(999)
        project_recycler.addItemDecoration(SpaceItemDecoration(20))
        project_recycler.adapter = projectNewAdapter
        project_refresh.setOnRefreshListener {
            if(!isRefresh) {
                isRefresh = true
                loadMore()
            }else {
                Toast.makeText(this.context, "正在刷新中", Toast.LENGTH_SHORT).show()
            }
        }
        project_refresh.setOnLoadMoreListener {
            if(!isLoadMore) {
                isLoadMore = true
                loadMore()
            }
        }
        project_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(recyclerView.scrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    (activity as MainIS).setNavigationVisibility(dy < 0)
                }
            }
        })
    }

    override fun initData() {
       project_refresh.autoRefresh()
    }

    private fun loadMore() {
        homeViewModel.getListProjects(curProjectPage)
    }

    override fun startObserve() {
        homeViewModel.listProjects.observe(this,{ list ->
            list?.datas?.let {
                if(isRefresh) {
                    project_refresh.finishRefresh()
                    projectNewAdapter.setData(it,true)
                    isRefresh = false
                    curProjectPage++
                }
                if(isLoadMore) {
                    project_refresh.finishLoadMore()
                    projectNewAdapter.setData(it)
                    isLoadMore = false
                    curProjectPage++
                }
            }
        })
    }

    override fun scrollToTop() {
        project_recycler.smoothScrollToPosition(0)
    }

    override fun init(context: Context?) {}
}