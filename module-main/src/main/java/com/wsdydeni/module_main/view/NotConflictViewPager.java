package com.wsdydeni.module_main.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class NotConflictViewPager extends ViewPager {
    public NotConflictViewPager(Context context) {
        super(context);
    }

    public NotConflictViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private float mX;
    private float mY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mX = ev.getX();
                mY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(Math.abs(mX - ev.getX()) > Math.abs(mY - ev.getY()));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
