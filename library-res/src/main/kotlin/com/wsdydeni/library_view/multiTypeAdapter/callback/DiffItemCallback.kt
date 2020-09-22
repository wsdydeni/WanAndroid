package com.wsdydeni.library_view.multiTypeAdapter.callback

import androidx.recyclerview.widget.DiffUtil
import com.wsdydeni.library_view.multiTypeAdapter.binder.MultiTypeBinder

/**
 * date          : 2019/5/31
 * author        : 秦川·小将
 * description   :
 */
class DiffItemCallback<T : MultiTypeBinder<*>> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.layoutId() == newItem.layoutId()
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.hashCode() == newItem.hashCode() && oldItem.areContentsTheSame(newItem)
    }

}