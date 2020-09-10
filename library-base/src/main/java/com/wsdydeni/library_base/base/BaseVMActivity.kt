package com.wsdydeni.library_base.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import kotlin.properties.Delegates

abstract class BaseVMActivity<T : ViewDataBinding,VM : BaseViewModel>(@LayoutRes private val resId: Int) : AppCompatActivity() {

    private lateinit var mBinding : T
    protected lateinit var mViewModel : VM
    private var mViewModelId by Delegates.notNull<Int>()

    private fun binding(@LayoutRes resId : Int) : T =
        DataBindingUtil.setContentView<T>(this,resId).apply {
            this.lifecycleOwner = this@BaseVMActivity
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = binding(resId)
        mViewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(initViewModel())
        mViewModelId = initViewModelId()
        mBinding.setVariable(mViewModelId,mViewModel)
        initView()
        initData()
        startObserve()
        super.onCreate(savedInstanceState)
    }

    abstract fun initView()

    abstract fun initData()

    abstract fun startObserve()

    abstract fun initViewModel() : Class<VM>

    abstract fun initViewModelId() : Int
}