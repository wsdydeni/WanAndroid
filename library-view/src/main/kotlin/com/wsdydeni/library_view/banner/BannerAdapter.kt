package com.wsdydeni.library_view.banner

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.wsdydeni.library_view.R

const val MAX_VALUE = 500

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.MyViewHolder>() {

    private var _onClick : ((Int) -> Unit)? = null

    fun setOnClickListener(listener: (Int) -> Unit) {
        _onClick = listener
    }

    private var mList = arrayListOf<BannerInfo>()

    fun setData(list: List<BannerInfo>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun getListSize() : Int = mList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false))

    override fun getItemCount(): Int = if (mList.size > 1) { MAX_VALUE } else { mList.size }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val realPosition = if(mList.size == 0) { 0 } else{ (position - 1 + mList.size) % mList.size }
        val image = holder.itemView.findViewById<ImageView>(R.id.banner_image)
        Glide.with(holder.itemView.context).asBitmap().load(mList[realPosition].imagePath).into(MyTarget(image))
        holder.itemView.setOnClickListener {
            _onClick?.invoke(realPosition)
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

class MyTarget(private val target: ImageView) : CustomViewTarget<ImageView, Bitmap>(target) {
    override fun onLoadFailed(errorDrawable: Drawable?) {}

    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
        getSize { width, height -> target.setImageBitmap(zoomImg(resource,width,height)) }
    }

    override fun onResourceCleared(placeholder: Drawable?) {}

}

fun zoomImg(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
    val width = bm.width
    val height = bm.height
    val scaleWidth = newWidth.toFloat() / width
    val scaleHeight = newHeight.toFloat() / height
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)
    return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)
}