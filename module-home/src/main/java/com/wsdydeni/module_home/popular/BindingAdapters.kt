package com.wsdydeni.module_home.popular

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView,imageUrl : String) {
    Glide.with(imageView).load(imageUrl).into(imageView)
}