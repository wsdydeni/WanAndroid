package com.wsdydeni.module_main.home.popular

import android.graphics.Color
import androidx.recyclerview.widget.*
import com.wsdydeni.library_view.SpaceItemDecoration
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.databinding.HomeItemPopularBinding
import com.wsdydeni.module_main.home.popular.article.ArticleAdapter
import com.wsdydeni.module_main.home.popular.bean.Article
import com.wsdydeni.module_main.home.popular.project.ProjectAdapter

class PopularBinder : MultiTypeBinder<HomeItemPopularBinding>() {

    var isArticled = true
        private set

    private val articleAdapter = ArticleAdapter().apply {
        setOnClickListener { onClick?.invoke(it) }
    }

    private val projectAdapter = ProjectAdapter().apply {
        setOnClickListener { onClick?.invoke(it) }
    }

    fun setOnClick(action: (String) -> Unit) {
        onClick = action
    }

    private var onClick : ((String) -> Unit)? = null

    override fun layoutId(): Int = R.layout.home_item_popular

    override fun areContentsTheSame(other: Any): Boolean = other is PopularBinder

    fun changeArticle(isArticle: Boolean) {
        isArticled = isArticle
        binding?.homePopularRecycler?.adapter = null
        binding?.homePopularArticle?.setTextColor(if (isArticle) Color.parseColor("#2EB2B2") else Color.parseColor("#333333"))
        binding?.homePopularProject?.setTextColor(if (!isArticle) Color.parseColor("#2EB2B2") else Color.parseColor("#333333"))
    }

    fun changeData(dataList: ArrayList<Article>, isTop: Boolean, clear: Boolean) {
        binding?.homePopularRecycler?.let {
            it.adapter = if(isArticled) articleAdapter else projectAdapter
        }
        if(isArticled) {
            if(isTop) articleAdapter.setTopData(dataList) else articleAdapter.addData(dataList,clear)
        }else {
            projectAdapter.setData(dataList,clear)
        }
    }

    override fun onBindViewHolder(binding: HomeItemPopularBinding) {
        binding.homePopularArticle.setTextColor(Color.parseColor("#2EB2B2"))
        binding.homePopularRecycler.addItemDecoration(SpaceItemDecoration(20))
        binding.homePopularRecycler.layoutManager = LinearLayoutManager(binding.root.context)
    }
}