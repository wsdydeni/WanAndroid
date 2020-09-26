package com.wsdydeni.library_base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.ext.logD
import com.wsdydeni.library_base.utils.StatusUtil


abstract class BaseVMActivity: AppCompatActivity() {

    protected abstract fun getDataBindingConfig(): DataBindingConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        StatusUtil.setStatusBar(this)
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        initDataBinding()
        startObserve()
        initView()
        initData()
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }

    private fun initDataBinding() {
        "${javaClass.simpleName} initDataBinding START".logD("DataBindingActivity")
        with(getDataBindingConfig()) {
            val binding: ViewDataBinding =
                DataBindingUtil.setContentView(this@BaseVMActivity, getLayoutId())
            binding.apply {
                lifecycleOwner = this@BaseVMActivity
                setVariable(getVariableId(), getViewModel())
                getBindingParams()?.forEach { key: Int, any: Any ->
                    setVariable(key, any)
                }
            }
        }
        "${javaClass.simpleName} initDataBinding END".logD("DataBindingActivity")
    }

    abstract fun initView()

    abstract fun initData()

    abstract fun startObserve()

}