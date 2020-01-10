package com.shushan.manhua.mvp.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import com.shushan.manhua.entity.constants.Constant;

/**
 * 设置屏幕亮度
 */
public class BrightnessTools {

    /**
     * 设置屏幕亮度
     * brightness：0～255。
     */
    public static void setWindowBrightness(int brightness, Activity context, SharePreferenceUtil sharePreferenceUtil) {
        Window window = context.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255.0f;
        window.setAttributes(lp);
        saveWindowBrightness(brightness, sharePreferenceUtil);
    }

    /**
     * 保存屏幕亮度
     */
    private static void saveWindowBrightness(int brightness, SharePreferenceUtil sharePreferenceUtil) {
        sharePreferenceUtil.setData(Constant.SET_LING, brightness);
    }

    /**
     * 获取屏幕亮度
     */
    public static int getWindowBrightness(SharePreferenceUtil sharePreferenceUtil) {
        return sharePreferenceUtil.getIntData(Constant.SET_LING);
    }


    /**
     * 获取系统亮度
     * 获取的值在0-255范围
     */
    public static int getSystemLing(Activity activity) {
        int nowBrightnessValue = 0;
        ContentResolver resolver = activity.getContentResolver();
        try {
            nowBrightnessValue = android.provider.Settings.System.getInt(
                    resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }
}
