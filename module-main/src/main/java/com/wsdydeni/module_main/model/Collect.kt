package com.wsdydeni.module_main.model


import com.google.gson.annotations.SerializedName

data class Collect(
    @SerializedName("author")
    val author: String,
    @SerializedName("chapterId")
    val chapterId: Int,
    @SerializedName("chapterName")
    val chapterName: String,
    @SerializedName("courseId")
    val courseId: Int,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("envelopePic")
    val envelopePic: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("niceDate")
    val niceDate: String,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("originId")
    val originId: Int,
    @SerializedName("publishTime")
    val publishTime: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("visible")
    val visible: Int,
    @SerializedName("zan")
    val zan: Int
)