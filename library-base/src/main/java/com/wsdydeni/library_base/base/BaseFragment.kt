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
import com.alibaba.android.arouter.launcher.ARouter
import kotlin.properties.Delegates

abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel>(@LayoutRes private val resId: Int) : Fragment() {

    protected lateinit var mBinding : T
    protected lateinit var mViewModel : VM
    private var mViewModelId by Delegates.notNull<Int>()

    private var inInit = false

    private fun binding(@NonNull inflater: LayoutInflater, @LayoutRes resId: Int, container: ViewGroup?) : T =
        DataBindingUtil.inflate<T>(inflater, resId, container, false).apply {
            lifecycleOwner = this@BaseFragment
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(!::mBinding.isInitialized) { //未初始化视图
            mBinding = binding(inflater, resId, container)
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        mViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(initViewModel())
        mViewModelId = initViewModelId()
        mBinding.setVariable(mViewModelId, mViewModel)
        initView()
        super.onViewCreated(view, savedInstanceState)
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

    abstract fun initViewModel() : Class<VM>

    abstract fun initViewModelId() : Int
}