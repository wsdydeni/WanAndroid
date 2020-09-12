package com.wsdydeni.library_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlin.properties.Delegates

abstract class BaseFragment<T : ViewDataBinding,VM : BaseViewModel>(@LayoutRes private val resId: Int) : Fragment() {

    protected lateinit var mBinding : T
    protected lateinit var mViewModel : VM
    private var mViewModelId by Delegates.notNull<Int>()

    private fun binding(@NonNull inflater: LayoutInflater, @LayoutRes resId: Int,container: ViewGroup?,attachToParent: Boolean) : T =
        DataBindingUtil.inflate<T>(inflater,resId,container,attachToParent).apply {
            lifecycleOwner = this@BaseFragment
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = binding(inflater,resId, container,false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(initViewModel())
        mViewModelId = initViewModelId()
        mBinding.setVariable(mViewModelId,mViewModel)
        initView()
        initData()
        startObserve()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun initView()

    abstract fun initData()

    abstract fun startObserve()

    abstract fun initViewModel() : Class<VM>

    abstract fun initViewModelId() : Int
}