package com.wsdydeni.library_base.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

public class StatusUtil {

    public static void setStatusBar(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    public static void setStatusBarPaddingAndHeight(View toolBar,Activity activity) {
        if (toolBar != null) {
            Resources resources = activity.getResources();
            int statusBarHeight = getStatusBarHeight(activity);
            toolBar.setPadding(toolBar.getPaddingLeft(), statusBarHeight, toolBar.getPaddingRight(), toolBar.getPaddingBottom());
            toolBar.getLayoutParams().height = statusBarHeight + (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, resources.getDisplayMetrics());
        }
    }

    private static int getStatusBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
