package com.wsdydeni.module_main.ui.background

import com.alibaba.android.arouter.facade.annotation.Route
import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.config.PathConfig
import com.wsdydeni.library_base.utils.StatusUtil
import com.wsdydeni.library_view.recyclerview.SpaceItemDecoration
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.ui.adpater.BackgroundAdapter
import kotlinx.android.synthetic.main.fragment_background.*

@Route(path = PathConfig.PATH_BACKGROUND)
class BackgroundFragment : BaseFragment() {

    private val backgroundViewModel by lazy { BackgroundViewModel() }

    private lateinit var adapter: BackgroundAdapter

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_background,BR.viewModel,backgroundViewModel)
    }

    override fun initView() {
        StatusUtil.setStatusBarPaddingAndHeight(background_toolbar,activity)
        adapter = BackgroundAdapter(activity ?: throw NullPointerException("空异常")).apply {
            setOnClickListener {
                // 传递点击的ID
            }
        }
        background_recycler.adapter = adapter
        background_recycler.addItemDecoration(SpaceItemDecoration(10))
    }

    override fun initData() {
        backgroundViewModel.getTreeData()
    }

    override fun startObserve() {
        backgroundViewModel.tree.observe(this,{
            it?.let { adapter.setData(it) }
        })
    }
}