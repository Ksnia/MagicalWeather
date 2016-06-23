package com.huaqin.android.weather.util;

import android.util.Log;

public final class LogEx {

    private static final boolean LOGCAT = false;

    public static void d(String strLog) {
        if (LOGCAT) {
            Log.d(GlobalConstant.TAG, "" + strLog);
        }
    }
}
