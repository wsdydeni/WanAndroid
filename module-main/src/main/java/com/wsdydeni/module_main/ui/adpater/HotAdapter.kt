package com.wsdydeni.module_main.ui.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.databinding.ItemHotBinding
import com.wsdydeni.module_main.model.SearchInfoItem

class HotAdapter : RecyclerView.Adapter<HotAdapter.HotViewHolder>(){

    private var dataList = arrayListOf<SearchInfoItem>()

    private lateinit var binding: ItemHotBinding

    fun setHotData(dataList: ArrayList<SearchInfoItem>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_hot,parent,false)
        return HotViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: HotViewHolder, position: Int) {
        binding.hot = dataList[position]
        binding.root.setOnClickListener {
            ARouter.getInstance().build("/main/DetailActivity").withString("text",dataList[position].name).navigation()
        }
        binding.executePendingBindings()
    }

    override fun getItemCount(): Int = dataList.size

    class HotViewHolder(content: View) : RecyclerView.ViewHolder(content)

}