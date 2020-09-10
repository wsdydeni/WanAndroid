package com.wsdydeni.library_base.network

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe

class ResultBuilder<T> {
    var onLoading: () -> Unit = {}
    var onEmpty: () -> Unit = {}
    var onSuccess: (data : T?) -> Unit = {}
    var onError: (Exception?) -> Unit = {}
}

@MainThread inline fun <T> LiveData<RequestState<T>>.observeState(owner : LifecycleOwner, builder: ResultBuilder<T>.() -> Unit) {
    val result = ResultBuilder<T>()
    result.builder()
    observe(owner) { state ->
        when (state) {
            is RequestState.Loading -> result.onLoading
            is RequestState.Empty -> result.onEmpty
            is RequestState.Success -> result.onSuccess(state.data)
            is RequestState.Error -> result.onError(state.error)
        }
    }
}

@MainThread inline fun <T> MutableLiveData<RequestState<T>>.observeState(owner : LifecycleOwner, builder: ResultBuilder<T>.() -> Unit) {
    val result = ResultBuilder<T>()
    result.builder()
    observe(owner) { state ->
        when (state) {
            is RequestState.Loading -> result.onLoading
            is RequestState.Empty -> result.onEmpty
            is RequestState.Success -> result.onSuccess(state.data)
            is RequestState.Error -> result.onError(state.error)
        }
    }
}



