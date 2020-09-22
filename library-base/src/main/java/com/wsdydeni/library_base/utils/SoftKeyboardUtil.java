package com.wsdydeni.library_base.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

// 鸣谢: 戴维尼老爷爷
// 链接: https://juejin.im/post/6844903471687172104

public class SoftKeyboardUtil {

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /*
     * 隐藏软键盘(可用于Activity，Fragment)
     */
//    public static void hideSoftKeyboard(Context context, List <View> viewList) {
//        if (viewList == null) return;
//
//        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
//
//        for (View v : viewList) {
//            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//    }
}
