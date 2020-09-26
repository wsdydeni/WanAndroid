package com.wsdydeni.library_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.ext.logD

abstract class BaseFragment: Fragment() {

    protected abstract fun getDataBindingConfig(): DataBindingConfig

    private var inInit = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initDataBinding(inflater, container)
    }

    private fun initDataBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        "${javaClass.simpleName} initDataBinding START".logD("DataBindingFragment")
        with(getDataBindingConfig()) {
            val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
            binding.apply {
                lifecycleOwner = this@BaseFragment
                setVariable(getVariableId(), getViewModel())
                getBindingParams()?.forEach { key: Int, any: Any ->
                    setVariable(key, any)
                }
                "${javaClass.simpleName} initDataBinding END".logD("DataBindingFragment")
                return root
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        if(!inInit && !isHidden) {
            initData()
            startObserve()
            inInit = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        inInit = false
    }

    abstract fun initView()

    abstract fun initData()

    abstract fun startObserve()
}