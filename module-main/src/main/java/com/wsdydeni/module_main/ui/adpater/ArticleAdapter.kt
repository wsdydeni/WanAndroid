package com.wsdydeni.module_main.ui.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.databinding.ItemArticleBinding
import com.wsdydeni.module_main.model.Article


class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(){

    private var dataList = arrayListOf<Article>()

    private lateinit var binding : ItemArticleBinding

    fun setData(newList: ArrayList<Article>,isRefresh: Boolean = false) {
        if(isRefresh) dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

    class ArticleViewHolder(contentView : View) : RecyclerView.ViewHolder(contentView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_article,parent,false)
        return ArticleViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        binding.article = dataList[holder.bindingAdapterPosition]
        binding.root.setOnClickListener {
            ARouter.getInstance().build("/browser/BrowserActivity")
                .withString("url",dataList[holder.bindingAdapterPosition].link).navigation()
        }
        binding.executePendingBindings()
    }

    override fun getItemCount(): Int = dataList.size
}
