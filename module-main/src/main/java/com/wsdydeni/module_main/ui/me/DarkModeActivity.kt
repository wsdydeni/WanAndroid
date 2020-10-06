package com.wsdydeni.module_main.ui.me

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.wsdydeni.library_base.base.BaseApplication
import com.wsdydeni.library_base.config.Config
import com.wsdydeni.library_base.config.PathConfig
import com.wsdydeni.library_base.utils.StatusUtil
import com.wsdydeni.module_main.R
import kotlinx.android.synthetic.main.activity_darkmode.*


@Route(path = PathConfig.PATH_DARK_MODE)
class DarkModeActivity : AppCompatActivity() {

    private var isDarkMode = false

    private var isFollow = false

    private var isFollowSystem = BaseApplication.mmkv?.decodeInt(Config.MODE_STATE, 1) == 0

    private var curMode = BaseApplication.mmkv?.decodeInt(Config.MODE_STATE, 1) == 2

    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        StatusUtil.setStatusBar(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_darkmode)
        StatusUtil.setStatusBarPaddingAndHeight(darkMode_toolbar, this)
        isFollow = isFollowSystem
        darkMode_switch.isChecked = isFollow
        setManualVisible(isFollow)
        isDarkMode = curMode
        setButtonVisible(isDarkMode)
        dialog = modeDialog()
        dark_mode_layout.setOnClickListener {
            isDarkMode = true
            setButtonVisible(isDarkMode)
        }
        normal_mode_layout.setOnClickListener {
            isDarkMode = false
            setButtonVisible(isDarkMode)
        }
        darkMode_toolbar.setOnMenuItemClickListener {
            if(it.itemId == R.id.choose_mode) {
                if(isDarkMode != curMode || isFollow != isFollowSystem) { dialog.show() }
            }
            return@setOnMenuItemClickListener true
        }
        darkMode_switch.setOnCheckedChangeListener { _, isChecked ->
            isFollow = isChecked
            setManualVisible(isChecked)
        }
        darkMode_toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setManualVisible(follow: Boolean) {
        manual_group.visibility = if(follow) View.GONE else View.VISIBLE
    }

    private fun setButtonVisible(isDark: Boolean) {
        normal_mode_image.visibility = if(isDark) View.INVISIBLE else View.VISIBLE
        dark_mode_image.visibility = if(isDark) View.VISIBLE else View.INVISIBLE
    }

    private fun modeDialog() : AlertDialog {
        return AlertDialog.Builder(this).apply {
            setMessage("新的模式需要重启后才能生效")
            setPositiveButton("重启") { dialog, _ ->
                Handler().postDelayed({
                    dialog.dismiss()
                    BaseApplication.mmkv?.encode(Config.MODE_STATE,if(isFollow) 0 else if(isDarkMode) 2 else 1)
                    val intent: Intent = packageManager.getLaunchIntentForPackage(packageName)!!
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                },1000L)
            }
            setNegativeButton("取消") { dialog, _ ->
                dialog.dismiss()
            }
            setCancelable(true)
        }.create()
    }
}