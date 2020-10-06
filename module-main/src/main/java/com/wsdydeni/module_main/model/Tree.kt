package com.wsdydeni.module_main.model

data class Tree(
    val children: List<TreeData>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)