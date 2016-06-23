package com.huaqin.android.weather.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private static Context mContext;
    private static PreferencesManager mInstance;

    public static PreferencesManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PreferencesManager(context);
        }
        return mInstance;
    }

    private PreferencesManager(Context context) {
        mContext = context;
    }

    public static void setTemperatureFormat(String strTemperatureFormat) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(
                GlobalConstant.Preference.PREFERENCES_KEY_TEMPERATURE_FORMAT,
                strTemperatureFormat);
        editor.commit();
    }

    public static String getTemperatureFormat() {
        return getSharedPreferences().getString(
                GlobalConstant.Preference.PREFERENCES_KEY_TEMPERATURE_FORMAT,
                GlobalConstant.Weather.WEATHER_TEMPERATURE_FORMAT_C);
    }

    public static void setUpdateFrequency(int times) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(
                GlobalConstant.Preference.PREFERENCES_KEY_UPDATE_FREQUENCY,
                times);
        editor.commit();
    }

    public static int getUpdateFrequency() {
        return getSharedPreferences().getInt(
                GlobalConstant.Preference.PREFERENCES_KEY_UPDATE_FREQUENCY,
                GlobalConstant.Weather.WEATHER_UPDATE_FREQUENCY);
    }

    public static void setWoeid(String woeid) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(GlobalConstant.Preference.PREFERENCES_KEY_WOEID, woeid);
        editor.commit();
    }

    public static String getWoeid() {
        return getSharedPreferences().getString(
                GlobalConstant.Preference.PREFERENCES_KEY_WOEID, "");
    }

    public static void setCity(String strCity) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(GlobalConstant.Preference.PREFERENCES_KEY_CITY,
                strCity);
        editor.commit();
    }

    public static String getCity() {
        return getSharedPreferences().getString(
                GlobalConstant.Preference.PREFERENCES_KEY_CITY, "");
    }

    public static void setUpdateTime(String strTime) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(GlobalConstant.Preference.PREFERENCES_KEY_UPDATE_TIME,
                strTime);
        editor.commit();
    }

    public static String getLastUpdateTime() {
        return getSharedPreferences().getString(
                GlobalConstant.Preference.PREFERENCES_KEY_UPDATE_TIME, "");
    }

    private static SharedPreferences getSharedPreferences() {
        return mContext.getApplicationContext().getSharedPreferences(
                GlobalConstant.Preference.PREFERENCES_NAME,
                Activity.MODE_PRIVATE);
    }
}
