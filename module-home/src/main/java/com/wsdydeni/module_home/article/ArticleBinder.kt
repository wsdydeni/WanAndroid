package com.wsdydeni.module_home.article

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder
import com.wsdydeni.module_home.R
import com.wsdydeni.module_home.databinding.HomeItemArticleBinding

class ArticleBinder(private val dataList : ArrayList<Article>) : MultiTypeBinder<HomeItemArticleBinding>(){

    private val articleAdapter = ArticleAdapter()

    override fun layoutId(): Int = R.layout.home_item_article

    override fun areContentsTheSame(other: Any): Boolean = other is ArticleBinder && other.dataList == other.dataList

    override fun onBindViewHolder(binding: HomeItemArticleBinding) {
        val articleRecycle = binding.homeTopRecycler
        articleRecycle.layoutManager = LinearLayoutManager(articleRecycle.context)
        articleRecycle.addItemDecoration(SpaceItemDecoration(20))
        articleRecycle.adapter = articleAdapter
        articleAdapter.setData(dataList)
    }
}

class SpaceItemDecoration(private val space : Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = space
    }
}