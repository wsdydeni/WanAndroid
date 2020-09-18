package com.wsdydeni.module_home.popular.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wsdydeni.module_home.R
import com.wsdydeni.module_home.databinding.ItemProjectBinding
import com.wsdydeni.module_home.popular.bean.Article

class ProjectAdapter : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>(){

    private lateinit var binding : ItemProjectBinding

    private var dataList = arrayListOf<Article>()

    fun setData(dataList : ArrayList<Article>,clear: Boolean) {
        if(clear) this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_project,parent,false)
        return ProjectViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        binding.project = dataList[position]
        binding.executePendingBindings()
    }

    override fun getItemCount(): Int = dataList.size

    class ProjectViewHolder(content : View) : RecyclerView.ViewHolder(content)

}