package com.wsdydeni.library_base.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers

open class BaseViewModel : ViewModel() {
    inline fun <T> launchOnIO(crossinline block: suspend () -> LiveData<T>) : LiveData<T> {
        return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(block())
        }
    }

    inline fun <T> launchOnUI(crossinline block: suspend () -> LiveData<T>) : LiveData<T> {
        return liveData(viewModelScope.coroutineContext + Dispatchers.Main){
            emitSource(block())
        }
    }
}