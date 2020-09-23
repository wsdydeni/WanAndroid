package com.wsdydeni.module_main.home

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.library_base.network.observeState
import com.wsdydeni.library_view.multiTypeAdapter.MultiTypeAdapter
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder
import com.wsdydeni.library_view.multiTypeAdapter.createMultiTypeAdapter
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.databinding.FragmentHomeBinding
import com.wsdydeni.module_main.binder.BannerBinder
import com.wsdydeni.module_main.binder.PopularBinder
import kotlinx.android.synthetic.main.fragment_home.*

@Route(path = "/main/HomeFragment")
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    private lateinit var recyclerAdapter: MultiTypeAdapter

    private lateinit var popularBinder: PopularBinder

    private var bannerBinder: BannerBinder? = null

    private var curArticlePage = 0

    private var curProjectPage = 0

    private val binderList = arrayListOf<MultiTypeBinder<*>>()

    private var isRefresh = false

    override fun initView() {
        home_toolbar.title = "欢迎来到玩Android"
        home_toolbar.setOnMenuItemClickListener {
            if(it.itemId == R.id.go_search) ARouter.getInstance().build("/main/SearchActivity").navigation()
            true
        }
        recyclerAdapter = createMultiTypeAdapter(mBinding.homeRecycler, LinearLayoutManager(context))
        home_recycler.itemAnimator = DefaultItemAnimator()
        home_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val manager = recyclerView.layoutManager as LinearLayoutManager
                when (recyclerView.scrollState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        bannerBinder?.stopLoop()
                        (activity as ISkill).setNavigationVisibility(dy < 0)
                    }
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        (activity as ISkill).setNavigationVisibility(manager.findFirstVisibleItemPosition() == 0)
                    }
                    RecyclerView.SCROLL_STATE_SETTLING -> {
                        bannerBinder?.startLoop()
                    }
                }
            }
        })
        home_fab.setOnClickListener { home_recycler.smoothScrollToPosition(1) }
        refreshLayout.setOnRefreshListener {
            if (!isRefresh) {
                loadMore()
                isRefresh = refreshLayout.isRefreshing
            }
        }
        refreshLayout.setOnLoadMoreListener {
            loadMore()
        }
    }

    private fun loadMore() {
        if(popularBinder.isArticled) {
            curArticlePage++
            mViewModel.getListProjects(curArticlePage)
        }else {
            curProjectPage++
            mViewModel.getTopProjects(curProjectPage)
        }
    }

    override fun initData() {
        mViewModel.getBanner()
        popularBinder = PopularBinder().apply {
            setOnClickListener { view, _ ->
                when(view.id) {
                    R.id.home_popular_article -> {
                        if(!isRefresh) {
                            this.changeArticle(true)
                            isRefresh = true
                            loadMore()
                        }
                    }
                    R.id.home_popular_project -> {
                        if(!isRefresh) {
                            this.changeArticle(false)
                            isRefresh = true
                            loadMore()
                        }
                    }
                }
            }
            setOnClick {
                ARouter.getInstance().build("/browser/BrowserActivity").withString("url",it).navigation()
            }
        }
        binderList.add(popularBinder)
        popularBinder.changeArticle(true)
        mViewModel.getTopArticles()
        mViewModel.getListProjects(curArticlePage)
    }

    override fun startObserve() {
        mViewModel.banner.observeState(this, onSuccess = { apiResponse ->
            apiResponse?.data?.let {
                bannerBinder = BannerBinder(it)
                binderList.addAll(0, listOf(bannerBinder!!))
                recyclerAdapter.notifyAdapterChanged(binderList)
            }
        })

        mViewModel.topArticles.observeState(this, onSuccess = { apiResponse ->
            apiResponse?.data?.let {
                popularBinder.changeData(it, isTop = true, clear = false)
                recyclerAdapter.notifyAdapterChanged(binderList)
            }
        })

        mViewModel.topProjects.observeState(this, onSuccess = { apiResponse ->
            if(isRefresh) refreshLayout.finishRefresh() else refreshLayout.finishLoadMore()
            apiResponse?.data?.datas?.let {
                if(isRefresh) {
                    isRefresh = false
                    popularBinder.changeData(it, isTop = false, clear = true)
                }else {
                    popularBinder.changeData(it, isTop = false, clear = false)
                }
                recyclerAdapter.notifyAdapterChanged(binderList)
            }
        },onError = {
            if(isRefresh) {
                isRefresh = false
                refreshLayout.finishRefresh(false)
            }else refreshLayout.finishLoadMore(false)
        })

        mViewModel.listArticles.observeState(this, onSuccess = { apiResponse ->
            if(isRefresh) refreshLayout.finishRefresh() else refreshLayout.finishLoadMore()
            apiResponse?.data?.datas?.let {
                if(isRefresh) {
                    isRefresh = false
                    popularBinder.changeData(it, false, clear = true)
                }else {
                    popularBinder.changeData(it, false, clear = false)
                }
                recyclerAdapter.notifyAdapterChanged(binderList)
            }
        },onError = {
            if(isRefresh) {
                isRefresh = false
                refreshLayout.finishRefresh(false)
            }else refreshLayout.finishLoadMore(false)
        })
    }

    override fun onResume() {
        super.onResume()
        bannerBinder?.let {
            if(!it.isLooping()) it.startLoop()
        }
    }

    override fun onPause() {
        super.onPause()
        bannerBinder?.stopLoop()
    }

    override fun initViewModel(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun initViewModelId(): Int = BR.viewModel

}