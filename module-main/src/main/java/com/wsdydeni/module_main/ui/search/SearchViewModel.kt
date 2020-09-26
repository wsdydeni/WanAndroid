package com.wsdydeni.module_main.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.wsdydeni.library_base.base.LiveCoroutinesViewModel
import com.wsdydeni.module_main.data.SearchRepository

class SearchViewModel : LiveCoroutinesViewModel() {

    private var curPage = 0

    private var key = ""

    private val repository by lazy { SearchRepository() }

    private val _hotSearchItems = MutableLiveData(false)

    private val _searchItems = MutableLiveData(false)

    var hotSearchItems = _hotSearchItems.switchMap {
        launchOnViewModelScope { repository.getHotSearch().asLiveData() }
    }

    var searchItems = _searchItems.switchMap {
        launchOnViewModelScope { repository.search(curPage, key).asLiveData() }
    }

    fun getHotSearchItem() {
        _hotSearchItems.value = true
    }

    fun getSearchItem(k: String,page: Int) {
        key = k
        _searchItems.value = true
        curPage = page
    }
}