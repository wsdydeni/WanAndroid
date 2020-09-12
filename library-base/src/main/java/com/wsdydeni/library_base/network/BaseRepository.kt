package com.wsdydeni.library_base.network

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class BaseRepository<S : ApiService>(var viewModelScope: CoroutineScope, serviceClass: Class<S>) {

    private fun getHttpClint() : OkHttpClient =
        OkHttpClient.Builder().callTimeout(20, TimeUnit.SECONDS).addInterceptor(LogInterceptor("https",true)).build()

    val service : S = Retrofit.Builder().client(getHttpClint())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://www.wanandroid.com/").build().create(serviceClass)

}

fun <T,S : ApiService> BaseRepository<S>.launchRequest(block: suspend () -> ApiResponse<T>, resultState: MutableLiveData<RequestState<ApiResponse<T>>>) {
    viewModelScope.launch {
        runCatching {
            resultState.value = RequestState.Loading
            withContext(Dispatchers.IO) { block() }
        }.onSuccess {
            if(it.data == null) resultState.value = RequestState.Empty
            else resultState.value = RequestState.Success(it)
        }.onFailure {
            resultState.value = RequestState.Error()
        }
    }
}