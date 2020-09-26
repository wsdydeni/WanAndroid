package com.wsdydeni.library_base.base

import com.wsdydeni.library_base.network.ApiException

class ResultBuilder<T> {
    var onLoading: () -> Unit = {}
    var onSuccess: (data: T?) -> Unit = {}
    var onError: (ApiException?) -> Unit = {}
}