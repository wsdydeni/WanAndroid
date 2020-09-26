package com.wsdydeni.module_browser

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.wsdydeni.library_base.utils.StatusUtil
import kotlinx.android.synthetic.main.activity_browser.*

// 鸣谢：luyao
// 链接：https://github.com/lulululbj/wanandroid/blob/mvvm-kotlin/app/src/main/java/luyao/wanandroid/ui/BrowserActivity.kt

@Route(path = "/browser/BrowserActivity")
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
        browser_toolbar.setNavigationOnClickListener { onBackPressed() }
        initWebView()
        initData()
    }

    private fun initData() {
        Log.e("BrowserActivity","url: $url")
        mWebView.loadUrl(url)
    }

    private fun initWebView() {
        browser_progressBar.progressDrawable = ResourcesCompat.getDrawable(resources,R.drawable.color_progressbar,null)
        if(null != mWebView.x5WebViewExtension) {
            Log.e("BrowserActivity","已加载了x5内核webview")
            // 竖直快速滑块，设置null可去除
            mWebView.x5WebViewExtension.setVerticalTrackDrawable(null)
//            // enable:true(日间模式)，enable：false（夜间模式）
//            mWebView.settingsExtension.setDayOrNight(false)
//            // 数据网络下无图（已加载的图片正常显示）
//            mWebView.settingsExtension.setPicModel(IX5WebSettingsExtension.PicModel_NetNoPic)
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
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(p0: WebView?, p1: Int) {
                    super.onProgressChanged(p0, p1)
                    browser_progressBar.progress = p1
                }

                override fun onReceivedTitle(p0: WebView?, p1: String?) {
                    super.onReceivedTitle(p0, p1)
                    p1?.let { browser_toolbar.title = p1 }
                }
            }
        }
    }

    override fun onBackPressed() {
        if(mWebView.canGoBack()) mWebView.goBack()
        else super.onBackPressed()
    }
}
