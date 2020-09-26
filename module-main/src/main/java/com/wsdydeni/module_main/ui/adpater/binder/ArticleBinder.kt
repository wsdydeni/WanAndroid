package com.wsdydeni.module_main.ui.adpater.binder

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsdydeni.library_view.SpaceItemDecoration
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.databinding.HomeItemArticleBinding
import com.wsdydeni.module_main.model.Article
import com.wsdydeni.module_main.ui.adpater.ArticleAdapter

class ArticleBinder : MultiTypeBinder<HomeItemArticleBinding>() {

    private val topArticleAdapter by lazy { ArticleAdapter() }

    private val listArticleAdapter by lazy { ArticleAdapter() }

    fun setTopData(dataList: ArrayList<Article>) {
        topArticleAdapter.setData(dataList,true)
    }

    fun setListData(dataList: ArrayList<Article>,isRefresh: Boolean = false) {
        listArticleAdapter.setData(dataList,isRefresh)
    }

    private val concatAdapter by lazy { ConcatAdapter(topArticleAdapter,listArticleAdapter) }

    override fun layoutId(): Int = R.layout.home_item_article

    override fun areContentsTheSame(other: Any): Boolean = other is ArticleBinder

    override fun onBindViewHolder(binding: HomeItemArticleBinding) {
        binding.itemArticleRecycler.layoutManager = LinearLayoutManager(binding.root.context)
        binding.itemArticleRecycler.addItemDecoration(SpaceItemDecoration(20))
        binding.itemArticleRecycler.setItemViewCacheSize(999)
        binding.itemArticleRecycler.adapter = concatAdapter
    }
}