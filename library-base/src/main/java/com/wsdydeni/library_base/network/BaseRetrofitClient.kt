package com.wsdydeni.library_base.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class BaseRetrofitClient {

    private fun getHttpClint() : OkHttpClient =
        OkHttpClient.Builder().callTimeout(20,TimeUnit.HOURS).addInterceptor(LogInterceptor("http",true)).build()

    fun <S> getService(serviceClass: Class<S>,baseURL: String): S =
        Retrofit.Builder().client(getHttpClint())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL).build().create(serviceClass)

}