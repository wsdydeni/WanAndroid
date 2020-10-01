package com.wsdydeni.module_main.ui.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.databinding.ItemProjectBinding
import com.wsdydeni.module_main.model.Article

class ProjectAdapter : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    private var dataList = arrayListOf<Article>()

    fun setData(newList: ArrayList<Article>,isRefresh: Boolean = false) {
        if(isRefresh) {
            dataList.clear()
            dataList.addAll(newList)
            notifyItemRangeChanged(0,newList.size)
        }else {
            dataList.addAll(newList)
            notifyItemRangeInserted(if(dataList.size == 0) 0 else dataList.size - 1,newList.size)
        }
    }

    private lateinit var binding : ItemProjectBinding

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        binding.project = dataList[position]
        binding.root.setOnClickListener {
            ARouter.getInstance().build("/browser/BrowserActivity")
                .withString("url",dataList[position].link).navigation()
        }
        binding.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_project,parent,false)
        return ProjectViewHolder(binding.root)
    }

    class ProjectViewHolder(content: View) : RecyclerView.ViewHolder(content)

    override fun getItemCount(): Int = dataList.size
}