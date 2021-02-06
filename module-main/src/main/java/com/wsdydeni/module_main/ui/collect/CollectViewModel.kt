package com.wsdydeni.module_main.ui.collect

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.wsdydeni.library_base.base.LiveCoroutinesViewModel
import com.wsdydeni.module_main.data.CollectRepository

class CollectViewModel : LiveCoroutinesViewModel() {

    private val repository by lazy { CollectRepository() }

    private var curPage = 0

    private var _collectList = MutableLiveData(false)

    var collectList = _collectList.switchMap {
        launchOnViewModelScope { repository.getCollectArticleList(curPage).asLiveData() }
    }

    fun getCollectArticleList(page: Int) {
        curPage = page
        _collectList.value = true
    }
}