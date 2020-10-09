package com.wsdydeni.module_main.ui.background

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.wsdydeni.library_base.base.LiveCoroutinesViewModel
import com.wsdydeni.module_main.data.BackgroundRepository

class BackgroundViewModel : LiveCoroutinesViewModel() {

    private val repository by lazy { BackgroundRepository() }

    private var _getTreeList = MutableLiveData(false)

    private var curArticlePage = 0

    private var cid = 0

    private val _listArticle = MutableLiveData(false)

    val tree = _getTreeList.switchMap {
        launchOnViewModelScope { repository.getTree().asLiveData() }
    }

    fun getTreeData() { _getTreeList.value = true }

    var listArticles = _listArticle.switchMap {
        launchOnViewModelScope { repository.getSystemDetail(curArticlePage,cid = cid).asLiveData() }
    }

    fun getListArticle(cid: Int,page: Int) {
        this.cid = cid
        curArticlePage = page
        _listArticle.value = true
    }
}