package com.wsdydeni.module_main.ui.home

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_view.recyclerview.SpaceItemDecoration
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.ui.adpater.ArticleAdapter
import kotlinx.android.synthetic.main.fragment_article.*


class ArticleFragment : BaseFragment(),HomeIS{

    private var isRefresh = false

    private var isLoadMore = false

    private var isFirst = false

    private var curProjectPage = 0

    private lateinit var homeViewModel : HomeViewModel

    private lateinit var articleRecyclerView: RecyclerView

    private val listArticleAdapter by lazy { ArticleAdapter() }

    override fun initView() {
        article_refresh.setOnRefreshListener {
            if(!isFirst) {
                isFirst = true
                loadMore()
            }else if(!isRefresh) {
                isRefresh = true
                loadMore()
            }
        }
        article_refresh.setOnLoadMoreListener {
            if(!isLoadMore) {
                isLoadMore = true
                loadMore()
            }
        }
        articleRecyclerView = article_recycler
        articleRecyclerView.apply {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(recyclerView.scrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        (activity as MainIS).setNavigationVisibility(dy < 0)
                    }
                }
            })
            adapter = listArticleAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(20))
        }
    }

    private fun loadMore() {
        homeViewModel.getListArticle(curProjectPage)
    }

    override fun initData() {
        homeViewModel.getTopArticle()
        article_refresh.autoRefresh()
    }

    override fun startObserve() {
        homeViewModel.topArticles.observe(this,{
            listArticleAdapter.setData(it,isTop = true)
        })
        homeViewModel.listArticles.observe(this,{ list ->
            list?.datas?.let {
                if(isFirst) {
                    article_refresh.finishRefresh()
                    listArticleAdapter.setData(it)
                    curProjectPage++
                }
                if(isRefresh) {
                    article_refresh.finishRefresh()
                    listArticleAdapter.setData(it,isRefresh = true)
                    isRefresh = false
                    curProjectPage++
                }
                if(isLoadMore) {
                    article_refresh.finishLoadMore()
                    listArticleAdapter.setData(it)
                    isLoadMore = false
                    curProjectPage++
                }
            }
        })
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        homeViewModel = HomeFragment.homeViewModel
        return DataBindingConfig(R.layout.fragment_article,BR.viewModel,homeViewModel)
    }

    override fun scrollToTop() {
        articleRecyclerView.smoothScrollToPosition(0)
    }

    override fun init(context: Context?) {}

}