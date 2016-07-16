package com.bianaiqi.util;

import android.util.Log;

/**
 * Project：Components
 * Description：
 * Created by gsoft2-3 on 16-5-24
 *
 * @version ${VERSION}
 */
public class MyLog {

    public static boolean DEBUG = true;
    public static String PREFIX = "Carrick_";

    public static void d(Class<?> clazz, String msg) {
        if (DEBUG) {
            Log.d(clazz.getSimpleName(), PREFIX + msg + "");
        }
    }


    public static void e(Class<?> clazz, String msg) {
        if (DEBUG) {
            Log.e(clazz.getSimpleName(), PREFIX + msg + "");
        }
    }

    public static void i(Class<?> clazz, String msg) {
        if (DEBUG) {
            Log.i(clazz.getSimpleName(), PREFIX + msg + "");
        }
    }

    public static void w(Class<?> clazz, String msg) {
        if (DEBUG) {
            Log.w(clazz.getSimpleName(), PREFIX + msg + "");
        }
    }
}
