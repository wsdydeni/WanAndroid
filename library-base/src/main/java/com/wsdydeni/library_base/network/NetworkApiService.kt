package com.wsdydeni.library_base.network

import com.wsdydeni.library_base.base.BaseApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkApiService {

    fun <S> getService(serviceClass : Class<S>,isLogin : Boolean) : S {
        return Retrofit.Builder()
            .client(getHttpClient(isLogin))
            .addConverterFactory(GsonConverterFactory.create(GsonGetter.gson))
            .baseUrl("https://www.wanandroid.com/")
            .build().create(serviceClass)
    }

    private fun getHttpClient(isLogin: Boolean): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.apply {
            callTimeout(20, TimeUnit.SECONDS)
            addInterceptor(LogInterceptor())
            if(isLogin) {
                addInterceptor(SaveCookiesInterceptor(BaseApplication.getApplication()))
            }else {
                addInterceptor(AddCookiesInterceptor(BaseApplication.getApplication()))
            }
        }
        return builder.build()
    }

    fun clearCookie() { SaveCookiesInterceptor.clearCookie(BaseApplication.getApplication())}

}