package com.huaqin.android.weather.util;

import android.content.Context;
import android.view.WindowManager;

/**
 * @className:DeviceParams.java
 * @Description:设备参数类
 * @author libingwu
 * 
 */
public class DeviceParams {

    /** DeviceParams实例 */
    private static DeviceParams sInstance;
    private static WindowManager sWindowManager;

    public static DeviceParams getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DeviceParams(context);
        }
        return sInstance;
    }

    @SuppressWarnings("deprecation")
    public static int getDeviceWidth() {
        if (null != sWindowManager) {
            return sWindowManager.getDefaultDisplay().getWidth();
        }
        return -1;
    }

    @SuppressWarnings("deprecation")
    public static int getDeviceHeight() {
        if (null != sWindowManager) {
            return sWindowManager.getDefaultDisplay().getHeight();
        }
        return -1;
    }

    private DeviceParams(Context context) {
        // 获取屏幕参数
        sWindowManager = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
    }
}