package com.wsdydeni.module_main.home.popular.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.databinding.ItemArticleBinding
import com.wsdydeni.module_main.home.popular.bean.Article

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private lateinit var binding: ItemArticleBinding

    private var topData = arrayListOf<Article>()

    private var allData = arrayListOf<Article>()

    fun setTopData(dataList: ArrayList<Article>) {
        allData.removeAll(topData)
        topData = dataList
        allData.addAll(0,topData)
        notifyDataSetChanged()
    }

    fun addData(dataList: ArrayList<Article>, clear: Boolean) {
        if(clear) allData.clear()
        allData.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_article,parent,false)
        return ArticleViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        binding.article = allData[position]
        binding.executePendingBindings()
    }

    override fun getItemCount(): Int = allData.size

    class ArticleViewHolder(contentView : View) : RecyclerView.ViewHolder(contentView)
}
