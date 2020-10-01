package com.wsdydeni.library_base.base

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wsdydeni.library_base.network.ApiException
import com.wsdydeni.library_base.network.RequestState

@MainThread
inline fun <T> LiveData<RequestState<T>>.observeState(
    owner: LifecycleOwner,
    crossinline onLoading: () -> Unit = {},
    crossinline onSuccess: (T?) -> Unit = {},
    crossinline onError: (ApiException?) -> Unit = {}
) {
    observe(owner) { state ->
        when (state) {
            is RequestState.Loading -> onLoading.invoke()
            is RequestState.Success -> onSuccess(state.data)
            is RequestState.Error -> onError(state.error)
        }
    }
}

@MainThread
inline fun <T> MutableLiveData<RequestState<T>>.observeState(
    owner: LifecycleOwner,
    crossinline onLoading: () -> Unit = {},
    crossinline onSuccess: (T?) -> Unit = {},
    crossinline onError: (ApiException?) -> Unit = {}
) {
    observe(owner) { state ->
        when (state) {
            is RequestState.Loading -> onLoading.invoke()
            is RequestState.Success ->  onSuccess(state.data)
            is RequestState.Error -> onError(state.error)
        }
    }
}
