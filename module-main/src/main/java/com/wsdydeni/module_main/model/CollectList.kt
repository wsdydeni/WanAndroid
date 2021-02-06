package com.wsdydeni.module_main.model


import com.google.gson.annotations.SerializedName

data class CollectList(
    @SerializedName("curPage")
    val curPage: Int,
    @SerializedName("datas")
    val collects: List<Collect>,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("over")
    val over: Boolean,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("total")
    val total: Int
)