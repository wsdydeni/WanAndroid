package com.wsdydeni.library_base.network

class ApiException(val errorMessage: String, val errorCode: Int)
    : Throwable()