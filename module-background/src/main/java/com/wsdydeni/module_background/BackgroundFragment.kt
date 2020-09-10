package com.wsdydeni.module_background

import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.module_background.databinding.FragmentBackgroundBinding

class BackgroundFragment : BaseFragment<FragmentBackgroundBinding,BackgroundViewModel>(R.layout.fragment_background) {

    override fun initView() {

    }

    override fun initData() {

    }

    override fun startObserve() {

    }

    override fun initViewModel(): Class<BackgroundViewModel> = BackgroundViewModel::class.java

    override fun initViewModelId(): Int = BR.viewModel

}