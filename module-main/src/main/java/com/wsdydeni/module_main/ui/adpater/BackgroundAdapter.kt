package com.wsdydeni.module_main.ui.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.databinding.ItemBackLayoutBinding
import com.wsdydeni.module_main.databinding.ItemBackgroundLayoutBinding
import com.wsdydeni.module_main.model.Tree
import com.wsdydeni.module_main.model.TreeData


class BackgroundAdapter(val context: Context) : RecyclerView.Adapter<BackgroundAdapter.BackgroundViewHolder>() {

    private lateinit var binding: ItemBackgroundLayoutBinding

    private var onClick: ((Int) -> Unit)? = null

    fun setOnClickListener(click: (Int) -> Unit) {
        onClick = click
    }

    private var dataList = arrayListOf<Tree>()

    fun setData(list: ArrayList<Tree>) {
        dataList = list
        notifyDataSetChanged()
    }

    class BackgroundViewHolder(content: View) : RecyclerView.ViewHolder(content)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BackgroundViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_background_layout,parent,false)
        return BackgroundViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BackgroundViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        binding.tree = dataList[position]
        binding.itemBackRecycler.layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.BASELINE
        }
        binding.itemBackRecycler.adapter = BackItemAdapter().apply {
            this.setData(dataList[position].children as ArrayList<TreeData>)
            setOnClickListener {
                onClick?.invoke(it)
            }
        }
        binding.executePendingBindings()
    }

    override fun getItemCount(): Int = dataList.size
}

class BackItemAdapter : RecyclerView.Adapter<BackItemAdapter.BackItemViewHolder>() {

    private lateinit var binding: ItemBackLayoutBinding

    private var dataList = arrayListOf<TreeData>()

    private var onClick: ((Int) -> Unit)? = null

    fun setOnClickListener(click: (Int) -> Unit) {
        onClick = click
    }

    fun setData(list: ArrayList<TreeData>) {
        dataList = list
        notifyDataSetChanged()
    }

    class BackItemViewHolder(content: View) : RecyclerView.ViewHolder(content)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BackItemViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_back_layout,parent,false)
        return BackItemViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BackItemViewHolder, position: Int) {
        binding.treeData = dataList[position]
        binding.itemBackText.setOnClickListener {
            onClick?.invoke(dataList[position].id)
        }
        binding.executePendingBindings()
    }

    override fun getItemCount(): Int = dataList.size
}