package com.wsdydeni.module_search.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder
import com.wsdydeni.module_search.R
import com.wsdydeni.module_search.bean.SearchInfoItem
import com.wsdydeni.module_search.databinding.ItemHotBinding
import com.wsdydeni.module_search.databinding.SearchItemHotBinding

class HotBinder : MultiTypeBinder<SearchItemHotBinding>() {

    private val hotAdapter = HotAdapter()

    override fun onBindViewHolder(binding: SearchItemHotBinding) {
        super.onBindViewHolder(binding)
        binding.searchHotRecycler.layoutManager = FlexboxLayoutManager(binding.root.context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }
        binding.searchHotRecycler.adapter = hotAdapter
    }

    fun setHotData(dataList: ArrayList<SearchInfoItem>) {
        hotAdapter.setHotData(dataList)
    }

    override fun layoutId(): Int = R.layout.search_item_hot

    override fun areContentsTheSame(other: Any): Boolean = other is HotBinder
}

class HotAdapter : RecyclerView.Adapter<HotAdapter.HotViewHolder>(){

    private var dataList = arrayListOf<SearchInfoItem>()

    private lateinit var binding: ItemHotBinding

    fun setHotData(dataList: ArrayList<SearchInfoItem>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_hot,parent,false)
        return HotViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: HotViewHolder, position: Int) {
        binding.hot = dataList[position]
        binding.executePendingBindings()
    }

    override fun getItemCount(): Int = dataList.size

    class HotViewHolder(content: View) : RecyclerView.ViewHolder(content)

}