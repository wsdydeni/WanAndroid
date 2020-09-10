package com.wsdydeni.library_base.network

object ApiRetrofitClient : BaseRetrofitClient() {
    val service by lazy { getService(ApiService::class.java, "BaseUrl") }
}