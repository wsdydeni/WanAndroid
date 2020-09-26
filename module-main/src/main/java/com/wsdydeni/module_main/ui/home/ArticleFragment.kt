package com.wsdydeni.module_main.ui.home

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_view.multiTypeAdapter.MultiTypeAdapter
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder
import com.wsdydeni.library_view.multiTypeAdapter.createMultiTypeAdapter
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.ui.adpater.binder.ArticleBinder
import com.wsdydeni.module_main.ui.adpater.binder.BannerBinder
import kotlinx.android.synthetic.main.fragment_article.*

@Route(path = "/app/ArticleFragment")
class ArticleFragment : BaseFragment() {

    private var isRefresh = false

    private var isLoadMore = false

    private var curProjectPage = 0

    private lateinit var homeViewModel : HomeViewModel

    private var bannerBinder: BannerBinder? = null

    private var binderList: ArrayList<MultiTypeBinder<*>> = arrayListOf()

    private val articleBinder by lazy { ArticleBinder() }

    private lateinit var recyclerAdapter: MultiTypeAdapter

    override fun initView() {
        recyclerAdapter = createMultiTypeAdapter(article_recycler,LinearLayoutManager(this.activity))
        binderList.add(articleBinder)
        article_refresh.setOnRefreshListener {
            if(!isRefresh) {
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
        article_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val manager = recyclerView.layoutManager as LinearLayoutManager
                when (recyclerView.scrollState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        bannerBinder?.stopLoop()
                        (activity as MainIS).setNavigationVisibility(dy < 0)
                    }
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        homeViewModel.setTabVisible(manager.findFirstVisibleItemPosition() == 0)
                    }
                    RecyclerView.SCROLL_STATE_SETTLING -> {
                        bannerBinder?.startLoop()
                    }
                }
            }
        })
    }

    private fun loadMore() {
        homeViewModel.getListArticle(curProjectPage)
    }

    override fun initData() {
        homeViewModel.apply {
            getBannerList()
            getTopArticle()
        }
        article_refresh.autoRefresh()
    }

    override fun startObserve() {
        homeViewModel.bannerList.observe(this,{
            if(null == bannerBinder) {
                bannerBinder = BannerBinder(it)
                binderList.add(0,bannerBinder!!)
            }else {
                bannerBinder?.setData(it)
            }
            recyclerAdapter.notifyAdapterChanged(binderList)
        })
        homeViewModel.topArticles.observe(this,{
            articleBinder.setTopData(it)
            recyclerAdapter.notifyAdapterChanged(binderList)
        })
        homeViewModel.listArticles.observe(this,{ list ->
            list?.datas?.let {
                if(isRefresh) {
                    article_refresh.finishRefresh()
                    articleBinder.setListData(it,isRefresh)
                    isRefresh = false
                    curProjectPage++
                }
                if(isLoadMore) {
                    article_refresh.finishLoadMore()
                    articleBinder.setListData(it)
                    isLoadMore = false
                    curProjectPage++
                }
                recyclerAdapter.notifyAdapterChanged(binderList)
            }
        })
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        homeViewModel = HomeFragment.homeViewModel
        return DataBindingConfig(R.layout.fragment_article,BR.viewModel,homeViewModel)
    }

    override fun onResume() {
        super.onResume()
        bannerBinder?.startLoop()
    }

    override fun onPause() {
        super.onPause()
        bannerBinder?.stopLoop()
    }

}