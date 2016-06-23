package com.huaqin.android.weather.ui;

import com.huaqin.android.weather.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

/**
 * @Description Cities manager screen
 * @author MG.Rong
 * @version 1.0
 * @date 2013-8-22
 */
public class SettingCitiesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createViewAndFragment();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void createViewAndFragment() {
		setContentView(R.layout.settingcities_activity);

		final FragmentManager fragmentManager = getFragmentManager();
		final String Setting_TAG = "settingcities-list";
		CityManagerFragment citiesSetting = (CityManagerFragment) fragmentManager
				.findFragmentByTag(Setting_TAG);
		if (null == citiesSetting) {
			citiesSetting = new CityManagerFragment();
		}
		fragmentManager.executePendingTransactions();
	}
}
