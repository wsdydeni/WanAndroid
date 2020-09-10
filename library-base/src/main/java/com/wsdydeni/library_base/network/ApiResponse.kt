package com.wsdydeni.library_base.network

data class ApiResponse<out T> (
    val errorCode : Int,
    val errorMsg : String,
    val data : T,
)