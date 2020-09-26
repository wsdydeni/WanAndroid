package com.wsdydeni.module_main.ui.me

import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.library_base.base.AppViewModel
import com.wsdydeni.library_base.base.BaseFragment
import com.wsdydeni.library_base.base.config.DataBindingConfig
import com.wsdydeni.module_main.BR
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.ui.adpater.Setting
import com.wsdydeni.module_main.ui.adpater.SettingAdapter
import kotlinx.android.synthetic.main.fragment_me.*

class MeFragment : BaseFragment() {

    private val meViewModel by lazy { MeViewModel() }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_me,BR.viewModel,meViewModel).addBindingParam(BR.app,AppViewModel)
    }

    override fun initView() {
        me_collapsing_toolbar.title = "Title"
        me_collapsing_toolbar.setExpandedTitleColor(ResourcesCompat.getColor(resources,android.R.color.white,null))
        me_collapsing_toolbar.setCollapsedTitleTextColor(ResourcesCompat.getColor(resources,android.R.color.white,null))
    }

    override fun initData() {
        me_go_login.setOnClickListener {
            ARouter.getInstance().build("/sign/LoginActivity").navigation()
        }
    }

    override fun startObserve() {
        AppViewModel.loginState.observe(this, {
            if (it) initSetting()
        })
    }

    private fun initSetting() {
        val settingList = arrayListOf(
            Setting(getDrawable(R.drawable.ic_setting_favorites), "我的收藏"),
            Setting(getDrawable(R.drawable.ic_setting_history), "稍后阅读"),
            Setting(getDrawable(R.drawable.ic_setting_share), "我的分享"),
            Setting(getDrawable(R.drawable.ic_setting_listing), "听听音乐"),
            Setting(getDrawable(R.drawable.ic_setting_github), "推荐项目"),
            Setting(getDrawable(R.drawable.ic_setting_help), "关于我们"),
            Setting(getDrawable(R.drawable.ic_setting_set), "系统设置"),
            Setting(getDrawable(R.drawable.ic_setting_logout), "退出登录")
        )
        val settingAdapter = SettingAdapter().apply {
            setDataList(dataList = settingList)
            setOnClickListener { Toast.makeText(context, "点击了位置: $it", Toast.LENGTH_SHORT).show() }
        }
        me_setting_recycler.apply {
            adapter = settingAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun getDrawable(resourceId: Int) : Drawable =
        ResourcesCompat.getDrawable(resources, resourceId, null) ?: throw NullPointerException("没有找到该资源")

}