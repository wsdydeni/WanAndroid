package com.wsdydeni.module_search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wsdydeni.module_search.bean.Article
import com.wsdydeni.module_search.databinding.ItemSearchBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){

    private lateinit var binding: ItemSearchBinding

    private var allData = arrayListOf<Article>()

    fun setOnClickListener(action: (String) -> Unit) {
        onClick = action
    }

    private var onClick : ((String) -> Unit)? = null

    fun setData(dataList: ArrayList<Article>){
        allData.addAll(dataList)
        notifyDataSetChanged()
    }

    fun clearData() {
        allData.clear()
        notifyDataSetChanged()
    }

    class SearchViewHolder(content: View) : RecyclerView.ViewHolder(content)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_search,parent,false)
        return SearchViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        binding.search = allData[position]
        binding.root.setOnClickListener {
            onClick?.invoke(allData[position].link)
        }
        binding.executePendingBindings()
    }

    override fun getItemCount(): Int = allData.size
}