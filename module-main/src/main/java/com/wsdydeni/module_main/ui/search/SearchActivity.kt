package com.wsdydeni.module_main.ui.search

import android.content.res.Configuration
import android.view.inputmethod.EditorInfo
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.flexbox.*
import com.wsdydeni.library_base.base.BaseVMActivity
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.utils.SoftKeyboardUtil
import com.wsdydeni.library_base.utils.StatusUtil
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.ui.adpater.HotAdapter
import kotlinx.android.synthetic.main.activity_search.*


@Route(path = "/main/SearchActivity")
class SearchActivity : BaseVMActivity() {

    private lateinit var text : String

    private val searViewModel by lazy { SearchViewModel() }

    private val hotAdapter by lazy { HotAdapter() }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_search,BR.viewModel,searViewModel)
    }

    override fun initView() {
        StatusUtil.setStatusBarPaddingAndHeight(search_toolbar,this)
        search_toolbar.setNavigationOnClickListener { onBackPressed() }
        search_edit.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                SoftKeyboardUtil.hideSoftKeyboard(this)
                text = if(search_edit.text.isNullOrEmpty()) {
                    search_edit.hint.toString()
                }else {
                    search_edit.text.toString()
                }
                ARouter.getInstance().build("/main/DetailActivity").withString("text",text).navigation()
                return@setOnEditorActionListener true
            }
            false
        }
        search_hot_recycler.layoutManager = FlexboxLayoutManager(this).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.BASELINE
        }
        search_hot_recycler.adapter = hotAdapter
    }

    override fun initData() {
        searViewModel.getHotSearchItem()
    }

    override fun startObserve() {
        searViewModel.hotSearchItems.observe(this,{
            hotAdapter.setHotData(it)
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {} // 夜间模式未启用，使用浅色主题
            Configuration.UI_MODE_NIGHT_YES -> {} // 夜间模式启用，使用深色主题
        }
    }

}
