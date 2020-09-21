package com.wsdydeni.library_base.network

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseRepository(var viewModelScope: CoroutineScope)

fun <T> BaseRepository.launchRequest(block: suspend () -> ApiResponse<T>, resultState: MutableLiveData<RequestState<ApiResponse<T>>>) {
    viewModelScope.launch {
        runCatching {
            resultState.value = RequestState.Loading
            withContext(Dispatchers.IO) { block() }
        }.onSuccess {
            if(it.errorCode < 0) resultState.value = RequestState.Error(Exception(it.errorMsg))
            resultState.value = RequestState.Success(it)
        }.onFailure {
            resultState.value = RequestState.Error(Exception(it.message))
        }
    }
}