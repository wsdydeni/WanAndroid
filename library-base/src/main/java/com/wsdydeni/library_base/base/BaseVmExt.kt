package com.wsdydeni.library_base.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wsdydeni.library_base.ext.logE
import com.wsdydeni.library_base.network.ApiException
import com.wsdydeni.library_base.network.RequestState
import com.wsdydeni.library_base.utils.ExceptionUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @Author:BaiYu
 * @Time:2020/7/27 9:12
 */

@ExperimentalCoroutinesApi
fun <T> BaseViewModel.flowRequest(
    request: suspend () -> Flow<T>,
    resultState: MutableLiveData<RequestState<T>>,
    isShowDialog: Boolean = true,
    loadingMessage: String = "数据加载中..."
) {
    viewModelScope.launch(Dispatchers.Main) {
        request().onStart {
//            if (isShowDialog) loadingChange.showDialog.postEventValue(loadingMessage)
        }.onCompletion {
//            loadingChange.dismissDialog.postEventValue(false)
        }.catch {
//            loadingChange.dismissDialog.postEventValue(false)
            resultState.value = RequestState.Error(ExceptionUtil.getApiException(it))
            it.toString().logE("flowRequest catch")
        }.collectLatest {
            resultState.value = RequestState.Success(it)
        }
    }
}

@ExperimentalCoroutinesApi
fun <T> BaseViewModel.flowRequestNoCheck(
    request: suspend () -> Flow<T>,
    success: (T) -> Unit,
    error: (ApiException) -> Unit = {},
    isShowDialog: Boolean = true,
    loadingMessage: String = "数据加载中..."
) {
    viewModelScope.launch(Dispatchers.IO) {
        request().onStart {
            if (isShowDialog) loadingChange.showDialog.postEventValue(loadingMessage)
        }.onCompletion {
            loadingChange.dismissDialog.postEventValue(false)
        }.catch {
            loadingChange.dismissDialog.postEventValue(false)
            error(ExceptionUtil.getApiException(it))
            it.toString().logE("flowRequest catch")
        }.collectLatest {
            success(it)
        }
    }
}
