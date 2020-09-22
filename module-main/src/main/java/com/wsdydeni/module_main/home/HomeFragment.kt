package com.wsdydeni.module_main.home

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.library_base.network.observeState
import com.wsdydeni.library_view.RecyclerViewUtil
import com.wsdydeni.library_view.multiTypeAdapter.MultiTypeAdapter
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder
import com.wsdydeni.library_view.multiTypeAdapter.createMultiTypeAdapter
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.databinding.FragmentHomeBinding
import com.wsdydeni.module_main.home.banner.BannerBinder
import com.wsdydeni.module_main.home.popular.PopularBinder
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

    private lateinit var mRecyclerViewUtil : RecyclerViewUtil

    override fun initView() {
//        activity?.window?.statusBarColor = resources.getColor(android.R.color.white,null)
        home_search.setOnClickListener {
            ARouter.getInstance().build("/search/SearchActivity").navigation()
        }
        recyclerAdapter = createMultiTypeAdapter(mBinding.homeRecycler, LinearLayoutManager(context))
        home_recycler.itemAnimator = DefaultItemAnimator()
        home_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val manager = recyclerView.layoutManager as LinearLayoutManager
                if(recyclerView.scrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    (activity as ISkill).setNavigationVisibility(dy < 0)
                }else if(recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                    (activity as ISkill).setNavigationVisibility(manager.findFirstVisibleItemPosition() == 0)
                }
            }
        })
        home_fab.setOnClickListener { home_recycler.smoothScrollToPosition(1) }
        mRecyclerViewUtil = RecyclerViewUtil(home_recycler)
        mRecyclerViewUtil.setRecyclerViewLoadMoreListener {
            mRecyclerViewUtil.setLoadMoreEnable(false)
            loadMore()
        }
        home_refresh.setOnRefreshListener {
            if (!isRefresh) {
                loadMore()
                isRefresh = home_refresh.isRefreshing
            }
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
                            home_refresh.isRefreshing = true
                            isRefresh = true
                            loadMore()
                        }
                    }
                    R.id.home_popular_project -> {
                        if(!isRefresh) {
                            this.changeArticle(false)
                            home_refresh.isRefreshing = true
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
            apiResponse?.data?.datas?.let {
                if(isRefresh && !popularBinder.isArticled) {
                    popularBinder.changeData(it, isTop = false, clear = true)
                    home_refresh.isRefreshing = false
                    isRefresh = false
                }else {
                    popularBinder.changeData(it, isTop = false, clear = false)
                    mRecyclerViewUtil.setLoadMoreEnable(true)
                }
                recyclerAdapter.notifyAdapterChanged(binderList)
            }
        })

        mViewModel.listArticles.observeState(this, onSuccess = { apiResponse ->
            apiResponse?.data?.datas?.let {
                if(isRefresh && popularBinder.isArticled) {
                    popularBinder.changeData(it, false, clear = true)
                    home_refresh.isRefreshing = false
                    isRefresh = false
                }else {
                    popularBinder.changeData(it, false, clear = false)
                    mRecyclerViewUtil.setLoadMoreEnable(true)
                }
                recyclerAdapter.notifyAdapterChanged(binderList)
            }
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