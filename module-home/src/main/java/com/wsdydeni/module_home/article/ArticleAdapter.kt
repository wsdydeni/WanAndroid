package com.wsdydeni.module_home.article

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wsdydeni.module_home.R

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var dataList = arrayListOf<Article>()

    fun setData(dataList : ArrayList<Article>) {
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_article,parent,false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.item_article_title).text = dataList[position].title
        holder.itemView.findViewById<TextView>(R.id.item_article_author).text =
            if(dataList[position].author != "") "作者:" else "分享人:"
        holder.itemView.findViewById<TextView>(R.id.item_article_author_title).text =
            if(dataList[position].author != "") dataList[position].author else dataList[position].shareUser
        holder.itemView.findViewById<TextView>(R.id.item_article_time_title).text = dataList[position].niceDate
        holder.itemView.findViewById<TextView>(R.id.item_article_category_title).text =
            dataList[position].superChapterName + "/" + dataList[position].chapterName
    }

    override fun getItemCount(): Int = dataList.size

    class ArticleViewHolder(contentView : View) : RecyclerView.ViewHolder(contentView)
}