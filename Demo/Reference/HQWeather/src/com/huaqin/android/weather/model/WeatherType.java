package com.huaqin.android.weather.model;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import com.huaqin.android.weather.R;
import com.huaqin.android.weather.util.LogEx;

public final class WeatherType {

    /** Map Inex */
    @SuppressLint("UseSparseArrays")
    private static final Map<Integer, Integer> WeatherMap = new HashMap<Integer, Integer>();

    private static final int[] WEATHER_ICON = { R.drawable.weather_0,
            R.drawable.weather_1, R.drawable.weather_2, R.drawable.weather_3,
            R.drawable.weather_4, R.drawable.weather_5, R.drawable.weather_6,
            R.drawable.weather_7, R.drawable.weather_8, R.drawable.weather_9,
            R.drawable.weather_10, R.drawable.weather_11,
            R.drawable.weather_12, R.drawable.weather_13,
            R.drawable.weather_14, R.drawable.weather_15,
            R.drawable.weather_16, R.drawable.weather_17,
            R.drawable.weather_18, R.drawable.weather_19,
            R.drawable.weather_20, R.drawable.weather_21,
            R.drawable.weather_22, R.drawable.weather_23,
            R.drawable.weather_24, R.drawable.weather_25,
            R.drawable.weather_26, R.drawable.weather_27,
            R.drawable.weather_28, R.drawable.weather_29,
            R.drawable.weather_30, R.drawable.weather_31,
            R.drawable.weather_32, R.drawable.weather_33,
            R.drawable.weather_34, R.drawable.weather_35,
            R.drawable.weather_36, R.drawable.weather_37,
            R.drawable.weather_38, R.drawable.weather_39,
            R.drawable.weather_40, R.drawable.weather_41,
            R.drawable.weather_42, R.drawable.weather_43,
            R.drawable.weather_44, R.drawable.weather_45,
            R.drawable.weather_46, R.drawable.weather_47, R.drawable.weather_na };

    static {
        WeatherMap.put(0, WEATHER_ICON[0]);
        WeatherMap.put(1, WEATHER_ICON[1]);
        WeatherMap.put(2, WEATHER_ICON[2]);
        WeatherMap.put(3, WEATHER_ICON[3]);
        WeatherMap.put(4, WEATHER_ICON[4]);
        WeatherMap.put(5, WEATHER_ICON[5]);
        WeatherMap.put(6, WEATHER_ICON[6]);
        WeatherMap.put(7, WEATHER_ICON[7]);
        WeatherMap.put(8, WEATHER_ICON[8]);
        WeatherMap.put(9, WEATHER_ICON[9]);
        WeatherMap.put(10, WEATHER_ICON[10]);
        WeatherMap.put(11, WEATHER_ICON[11]);
        WeatherMap.put(12, WEATHER_ICON[12]);
        WeatherMap.put(13, WEATHER_ICON[13]);
        WeatherMap.put(14, WEATHER_ICON[14]);
        WeatherMap.put(15, WEATHER_ICON[15]);
        WeatherMap.put(16, WEATHER_ICON[16]);
        WeatherMap.put(17, WEATHER_ICON[17]);
        WeatherMap.put(18, WEATHER_ICON[18]);
        WeatherMap.put(19, WEATHER_ICON[19]);
        WeatherMap.put(20, WEATHER_ICON[20]);
        WeatherMap.put(21, WEATHER_ICON[21]);
        WeatherMap.put(22, WEATHER_ICON[22]);
        WeatherMap.put(23, WEATHER_ICON[23]);
        WeatherMap.put(24, WEATHER_ICON[24]);
        WeatherMap.put(25, WEATHER_ICON[25]);
        WeatherMap.put(26, WEATHER_ICON[26]);
        WeatherMap.put(27, WEATHER_ICON[27]);
        WeatherMap.put(28, WEATHER_ICON[28]);
        WeatherMap.put(29, WEATHER_ICON[29]);
        WeatherMap.put(30, WEATHER_ICON[30]);
        WeatherMap.put(31, WEATHER_ICON[31]);
        WeatherMap.put(32, WEATHER_ICON[32]);
        WeatherMap.put(33, WEATHER_ICON[33]);
        WeatherMap.put(34, WEATHER_ICON[34]);
        WeatherMap.put(35, WEATHER_ICON[35]);
        WeatherMap.put(36, WEATHER_ICON[36]);
        WeatherMap.put(37, WEATHER_ICON[37]);
        WeatherMap.put(38, WEATHER_ICON[38]);
        WeatherMap.put(39, WEATHER_ICON[39]);
        WeatherMap.put(40, WEATHER_ICON[40]);
        WeatherMap.put(41, WEATHER_ICON[41]);
        WeatherMap.put(42, WEATHER_ICON[42]);
        WeatherMap.put(43, WEATHER_ICON[43]);
        WeatherMap.put(44, WEATHER_ICON[44]);
        WeatherMap.put(45, WEATHER_ICON[45]);
        WeatherMap.put(46, WEATHER_ICON[46]);
        WeatherMap.put(47, WEATHER_ICON[47]);
        WeatherMap.put(3200, WEATHER_ICON[48]);
    }

    private WeatherType() {
    }

    public static int getWeatherIcon(int code) {
        LogEx.d("" + code);
        Integer obj = WeatherMap.get(code);
        if (obj != null) {
            return obj.intValue();
        }
        return WEATHER_ICON[WEATHER_ICON.length - 1];
    }

}
