package com.huaqin.android.weather.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huaqin.android.weather.R;
import com.huaqin.android.weather.provider.WeatherUpdateService;
import com.huaqin.android.weather.util.GlobalConstant;
import com.huaqin.android.weather.util.PreferencesManager;
import com.huaqin.android.weather.util.ToastManager;

public class WeatherSetting extends PreferenceFragment implements
        OnSharedPreferenceChangeListener {

    private static final String KEY_TEMPERATURE_FORMAT = "temperature_format";
    private static final String KEY_CITY_NAME = "city_name";
    private static final String KEY_UPDATE_FREQUENCY = "update_frequency";
    private static final String KEY_UPDATE_WEATHER = "update_wether";

    private Preference mCityPref;
    private Preference mUpdateWeather;
    private ListPreference mTemperatureFormat;
    private ListPreference mUpdateFrequency;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.setting_weather);

        initUI();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	return super.onCreateView(inflater, container, savedInstanceState);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
        // set default city
        if (mCityPref != null) {
            mCityPref.setSummary(PreferencesManager.getCity());
            // update the city
            Intent intent = new Intent();
            intent.setAction(GlobalConstant.BroadCastAction.WEATHER_UPDATE_CITY);
            intent.putExtra(GlobalConstant.IntentTag.INTENT_WEATHER_CITY,
                    mCityPref.getSummary());
            getActivity().sendBroadcast(intent);
        }
        // set last update time
        if (mUpdateWeather != null) {
            mUpdateWeather.setSummary(PreferencesManager
                    .getLastUpdateTime());
        }
    }
    
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
            String key) {
        if (key.equals(KEY_TEMPERATURE_FORMAT)) {
            String value = mTemperatureFormat.getValue();
            mTemperatureFormat.setSummary(value);
            PreferencesManager.setTemperatureFormat(value);
            // send temperature format changed broadcast
            Intent intent = new Intent();
            intent.setAction(GlobalConstant.BroadCastAction.WEATHER_UPDATE_TEMP_FORMAT);
            getActivity().sendBroadcast(intent);
        } else if (key.equals(KEY_UPDATE_FREQUENCY)) {
            String value = mUpdateFrequency.getValue();
            int index = mUpdateFrequency.findIndexOfValue(value);
            mUpdateFrequency.setSummary(value);
            PreferencesManager
                    .setUpdateFrequency(GlobalConstant.Weather.FREQUENCY[index]);
            // starta update service
            Intent intent = new Intent(getActivity(),
                    WeatherUpdateService.class);
            intent.setAction(GlobalConstant.IntentAction.ACTION_FREQUENCY_UPDATE);
            getActivity().startService(intent);
        }
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            Preference preference) {
        if (preference == mCityPref) {
            Intent intent = new Intent(getActivity(), SettingCitiesActivity.class);
            getActivity().startActivity(intent);
            return true;
        } else if (preference == mUpdateWeather) {
            if (PreferencesManager.getCity().length() == 0) {
                ToastManager
                        .makeToast(GlobalConstant.Toast.TOAST_NOT_INITILIZE);
                return true;
            }
            // start update service
            Intent intent = new Intent(getActivity(),
                    WeatherUpdateService.class);
            intent.setAction(GlobalConstant.IntentAction.ACTION_UPDATE);
            getActivity().startService(intent);
            // close the setting window
            getActivity().finish();
            return true;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void initUI() {
        mCityPref = findPreference(KEY_CITY_NAME);
        mUpdateWeather = findPreference(KEY_UPDATE_WEATHER);
        mTemperatureFormat = (ListPreference) findPreference(KEY_TEMPERATURE_FORMAT);
        mUpdateFrequency = (ListPreference) findPreference(KEY_UPDATE_FREQUENCY);

        String[] temperatureFormats = getResources().getStringArray(
                R.array.temperature_format_values);
        String currentFormat = getTemperatureFormat();
        if (currentFormat == null) {
            currentFormat = "";
        }
        mTemperatureFormat.setEntries(temperatureFormats);
        mTemperatureFormat.setEntryValues(R.array.temperature_format_values);
        mTemperatureFormat.setValue(currentFormat);
        mTemperatureFormat.setSummary(currentFormat);

        String[] updateFrequency = getResources().getStringArray(
                R.array.update_frequency_values);
        String currentValues = "";
        if (updateFrequency != null && updateFrequency.length > 0) {
            currentValues = updateFrequency[getFrequencyIndex()];
        }
        mUpdateFrequency.setEntries(updateFrequency);
        mUpdateFrequency.setEntryValues(R.array.update_frequency_values);
        mUpdateFrequency.setValue(currentValues);
        mUpdateFrequency.setSummary(currentValues);
    }

    private String getTemperatureFormat() {
        return PreferencesManager.getTemperatureFormat();
    }

    private int getUpdateFrequency() {
        return PreferencesManager.getUpdateFrequency();
    }

    private int getFrequencyIndex() {
        for (int i = 0; i < GlobalConstant.Weather.FREQUENCY.length; i++) {
            if (getUpdateFrequency() == GlobalConstant.Weather.FREQUENCY[i]) {
                return i;
            }
        }
        return 0;
    }
}
