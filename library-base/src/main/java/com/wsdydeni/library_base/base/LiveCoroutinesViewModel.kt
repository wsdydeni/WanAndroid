package com.wsdydeni.library_base.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.wsdydeni.library_base.network.RequestState
import kotlinx.coroutines.Dispatchers

/**
 * @author Baiyu
 * @date :2020/9/13 12:04 PM September
 * @version: 1.0
 */
abstract class LiveCoroutinesViewModel : BaseViewModel() {

    inline fun <T> launchOnViewModelScope(crossinline block: suspend () -> LiveData<T>): LiveData<T> {
        return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(block())
        }
    }

    inline fun <T> launchOnViewModelRequestState(crossinline block: suspend () -> LiveData<RequestState<T>>): LiveData<RequestState<T>> {
        return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(block())
        }
    }
}