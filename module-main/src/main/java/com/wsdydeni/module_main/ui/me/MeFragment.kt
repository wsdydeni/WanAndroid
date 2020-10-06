package com.wsdydeni.module_main.ui.me

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.library_base.base.AppViewModel
import com.wsdydeni.library_base.base.BaseApplication
import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.library_base.config.Config
import com.wsdydeni.library_base.config.PathConfig
import com.wsdydeni.library_base.utils.StatusUtil
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.model.User
import kotlinx.android.synthetic.main.fragment_me.*


@Route(path = PathConfig.PATH_ME)
class MeFragment : BaseFragment() {

    private val meViewModel by lazy { MeViewModel() }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_me,BR.viewModel,meViewModel).addBindingParam(BR.app,AppViewModel)
    }

    override fun initView() {
        StatusUtil.setStatusBarPaddingAndHeight(me_header,this.activity)
    }

    override fun initData() {
        me_go_login_layout.setOnClickListener {
            ARouter.getInstance().build(PathConfig.PATH_LOGIN).navigation()
        }
        me_setting_layout.setOnClickListener {
            ARouter.getInstance().build(PathConfig.PATH_SETTING).navigation(activity,LoginNavigationCallbackImpl())
        }
        me_dayNight_layout.setOnClickListener {
            ARouter.getInstance().build(PathConfig.PATH_DARK_MODE).navigation(activity,LoginNavigationCallbackImpl())
        }
    }

    override fun startObserve() {
        AppViewModel.loginState.observe(this,{
            if(it) {
                meViewModel.user.value = BaseApplication.mmkv?.decodeParcelable(Config.LOGIN_USER,User::class.java) ?: throw NullPointerException("MMKV Not initialized")
            }
        })
    }

}