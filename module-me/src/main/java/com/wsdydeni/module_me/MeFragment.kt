package com.wsdydeni.module_me

import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.module_me.databinding.FragmentMeBinding

class MeFragment : BaseFragment<FragmentMeBinding,MeViewModel>(R.layout.fragment_me) {

    override fun initView() {

    }

    override fun initData() {

    }

    override fun startObserve() {

    }

    override fun initViewModel(): Class<MeViewModel> = MeViewModel::class.java

    override fun initViewModelId(): Int = BR.viewModel

}