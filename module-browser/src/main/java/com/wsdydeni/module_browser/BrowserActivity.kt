package com.wsdydeni.module_browser

import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.wsdydeni.library_base.base.BaseApplication
import com.wsdydeni.library_base.config.Config
import com.wsdydeni.library_base.config.PathConfig
import com.wsdydeni.library_base.utils.StatusUtil
import kotlinx.android.synthetic.main.activity_browser.*

// 鸣谢：luyao
// 链接：https://github.com/lulululbj/wanandroid/blob/mvvm-kotlin/app/src/main/java/luyao/wanandroid/ui/BrowserActivity.kt

@Route(path = PathConfig.PATH_BROWSER)
class BrowserActivity : AppCompatActivity() {

    @Autowired
    lateinit var url : String

    private lateinit var mWebView : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        StatusUtil.setStatusBar(this)
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setContentView(R.layout.activity_browser)
        StatusUtil.setStatusBarPaddingAndHeight(browser_toolbar,this)
        mWebView = browser_webView
        browser_toolbar.setTextSize(18f)
        browser_toolbar.setNavigationDrawable(ContextCompat.getDrawable(this,R.drawable.ic_detail_left_arrow)!!)
        browser_toolbar.setOnClickListener { onBackPressed() }
        initWebView()
        initData()
    }

    private fun initData() {
        mWebView.loadUrl(url)
    }

    private fun initWebView() {
        browser_progressBar.progressDrawable = ResourcesCompat.getDrawable(resources,R.drawable.color_progressbar,null)
        if(null != mWebView.x5WebViewExtension) {
            mWebView.x5WebViewExtension.setVerticalTrackDrawable(null)
        }
        if(null != mWebView.settingsExtension) {
            mWebView.settingsExtension.setDayOrNight(BaseApplication.mmkv?.decodeInt(Config.MODE_STATE,1) != 2)
        }
        mWebView.run {
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
                    super.onPageStarted(p0, p1, p2)
                    browser_progressBar.visibility = View.VISIBLE
                }

                override fun onPageFinished(p0: WebView?, p1: String?) {
                    super.onPageFinished(p0, p1)
                    browser_progressBar.visibility = View.GONE
                }

                override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
                    return p1?.startsWith("jianshu://") ?: false
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(p0: WebView?, p1: Int) {
                    super.onProgressChanged(p0, p1)
                    browser_progressBar.progress = p1
                }

                override fun onReceivedTitle(p0: WebView?, p1: String?) {
                    super.onReceivedTitle(p0, p1)
                    p1?.let { browser_toolbar.setText(p1) }
                }
            }
        }
    }

    override fun onBackPressed() {
        if(mWebView.canGoBack()) mWebView.goBack()
        else super.onBackPressed()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {} // 夜间模式未启用，使用浅色主题
            Configuration.UI_MODE_NIGHT_YES -> {} // 夜间模式启用，使用深色主题
        }
    }
}
