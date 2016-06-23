package com.huaqin.android.weather.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import com.huaqin.android.weather.R;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createViewAndFragment();
    }

    private void createViewAndFragment() {
        setContentView(R.layout.setting_activity);

        final FragmentManager fragmentManager = getFragmentManager();
        final String ALL_TAG = "tab-list";
        WeatherSetting weatherSetting = (WeatherSetting) fragmentManager
                .findFragmentByTag(ALL_TAG);
        if (null == weatherSetting) {
            weatherSetting = new WeatherSetting();
        }
        fragmentManager.executePendingTransactions();
    }
}
