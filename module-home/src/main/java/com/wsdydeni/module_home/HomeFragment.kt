package com.wsdydeni.module_home

import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.module_home.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(R.layout.fragment_home) {

    override fun initView() {

    }

    override fun initData() {

    }

    override fun startObserve() {

    }

    override fun initViewModel(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun initViewModelId(): Int = BR.viewModel

}