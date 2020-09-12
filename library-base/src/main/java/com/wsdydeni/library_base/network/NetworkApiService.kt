package com.wsdydeni.library_base.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkApiService {

    fun <S> getService(serviceClass : Class<S>) : S {
        return Retrofit.Builder()
            .client(getHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.wanandroid.com/")
            .build().create(serviceClass)
    }

    private fun getHttpClient(): OkHttpClient =
        OkHttpClient.Builder().callTimeout(20, TimeUnit.SECONDS).addInterceptor(LogInterceptor()).build()

}