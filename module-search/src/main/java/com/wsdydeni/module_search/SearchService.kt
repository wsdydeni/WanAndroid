package com.wsdydeni.module_search

import com.wsdydeni.library_base.network.ApiResponse
import com.wsdydeni.module_search.bean.SearchInfoItem
import retrofit2.http.GET

interface SearchService {

    @GET("hotkey/json")
    suspend fun getHotSearch() : ApiResponse<ArrayList<SearchInfoItem>>
}