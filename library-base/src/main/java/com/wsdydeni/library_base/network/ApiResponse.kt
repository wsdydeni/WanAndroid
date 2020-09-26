package com.wsdydeni.library_base.network

data class ApiResponse<T>(var code: Int, var msg: String, var data: T) : BaseResponse<T>() {

    override fun isSuccess() = code == 0

    override fun getResponseCode() = code

    override fun getResponseData() = data

    override fun getResponseMsg() = msg

}