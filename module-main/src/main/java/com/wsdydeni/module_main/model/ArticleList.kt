package com.wsdydeni.module_main.model

data class ArticleList(
    val curPage: Int,
    val datas: ArrayList<Article>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)