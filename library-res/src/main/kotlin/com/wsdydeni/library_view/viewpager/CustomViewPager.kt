package com.wsdydeni.library_view.viewpager

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class CustomViewPager : ViewPager {
    private var isCanScroll = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    fun setScanScroll(isCanScroll: Boolean) {
        this.isCanScroll = isCanScroll
    }

    override fun performClick(): Boolean {
        Log.e("CustomViewPager","performClick")
        return super.performClick()
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        if(arg0.action == MotionEvent.ACTION_UP) {
            performClick()
        }
        return isCanScroll && super.onTouchEvent(arg0)
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return isCanScroll && super.onInterceptTouchEvent(arg0)
    }
}