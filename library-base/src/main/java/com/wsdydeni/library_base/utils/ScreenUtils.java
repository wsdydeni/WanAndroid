package com.wsdydeni.library_base.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.blankj.utilcode.util.Utils;

// 鸣谢: 笔墨Android
// 链接: https://juejin.im/post/6844903693041532936
public class ScreenUtils {

    private static final String TAG = "StatusUtil";

    /**
     * 适配的主要代码
     *
     * @param activity        上下文
     * @param sizeInPx        你要适配的相应尺寸
     * @param isVerticalSlide 水平还是垂直为参考
     */
    public static void adaptScreen(final Activity activity, final int sizeInPx, final boolean isVerticalSlide) {
        // 系统的屏幕尺寸
        final DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
        // app整体的屏幕尺寸
        final DisplayMetrics appDm = Utils.getApp().getResources().getDisplayMetrics();
        // activity的屏幕尺寸
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        if (isVerticalSlide) {
            activityDm.density = activityDm.widthPixels / (float) sizeInPx;
            Log.e(TAG, "adaptScreen: " + activityDm.widthPixels );
        } else {
            activityDm.density = activityDm.heightPixels / (float) sizeInPx;
        }
        // 字体的缩放因子，这个是通过一个比例计算得来的！
        activityDm.scaledDensity = activityDm.density * (systemDm.scaledDensity / systemDm.density);
        // 计算得到相应的dpi
        activityDm.densityDpi = (int) (160 * activityDm.density);

        //进行相应的赋值操作
        appDm.density = activityDm.density;
        appDm.scaledDensity = activityDm.scaledDensity;
        appDm.densityDpi = activityDm.densityDpi;
    }

    /**
     * 解决Toast变小问题
     */
    public static void cancelAdaptScreen(final Activity activity) {
        final DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
        final DisplayMetrics appDm = Utils.getApp().getResources().getDisplayMetrics();
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        activityDm.density = systemDm.density;
        activityDm.scaledDensity = systemDm.scaledDensity;
        activityDm.densityDpi = systemDm.densityDpi;

        appDm.density = systemDm.density;
        appDm.scaledDensity = systemDm.scaledDensity;
        appDm.densityDpi = systemDm.densityDpi;
    }

    /**
     * 解决Toast变小问题
     */
    public static void restoreAdaptScreen(Activity activity, boolean isVerticalSlide, int sizeInPx) {
        final DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
        final DisplayMetrics appDm = Utils.getApp().getResources().getDisplayMetrics();
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        if (isVerticalSlide) {
            activityDm.density = activityDm.widthPixels / (float) sizeInPx;
        } else {
            activityDm.density = activityDm.heightPixels / (float) sizeInPx;
        }
        activityDm.scaledDensity = activityDm.density * (systemDm.scaledDensity / systemDm.density);
        activityDm.densityDpi = (int) (160 * activityDm.density);

        appDm.density = activityDm.density;
        appDm.scaledDensity = activityDm.scaledDensity;
        appDm.densityDpi = activityDm.densityDpi;
    }

}
