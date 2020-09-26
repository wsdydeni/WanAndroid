package com.wsdydeni.module_main.ui.adpater

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wsdydeni.module_main.R

class SettingAdapter : RecyclerView.Adapter<SettingAdapter.SettingViewHolder>(){

    private var settingList = arrayListOf<Setting>()

    private var onClick: ((Int) -> Unit?)? = null

    fun setOnClickListener(onClick: (Int) -> Unit) {
        this.onClick = onClick
    }

    fun setDataList(dataList: ArrayList<Setting>) {
        this.settingList = dataList
        notifyDataSetChanged()
    }

    class SettingViewHolder(content: View) : RecyclerView.ViewHolder(content)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder =
        SettingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_setting,parent,false))

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.me_setting_image).setImageDrawable(settingList[position].image)
        holder.itemView.findViewById<TextView>(R.id.me_setting_text).text = settingList[position].text
        holder.itemView.setOnClickListener { onClick?.invoke(position) }
    }

    override fun getItemCount(): Int = settingList.size
}

class Setting(val image: Drawable,val text: String)