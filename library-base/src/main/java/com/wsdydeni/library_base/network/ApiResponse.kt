package com.wsdydeni.library_base.network

data class ApiResponse<T>(var errorCode: Int, var errorMsg: String?, var data: T) : BaseResponse<T>() {

    override fun isSuccess() = errorCode >= 0

    override fun getResponseCode() = errorCode

    override fun getResponseData() = data

    override fun getResponseMsg() = errorMsg

}