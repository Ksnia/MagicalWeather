package com.huaqin.android.weather.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaqin.android.weather.R;
import com.huaqin.android.weather.util.GlobalConstant;
import com.huaqin.android.weather.util.LogEx;
import com.huaqin.android.weather.util.PreferencesManager;

public class SettingFragment extends CommonFragment implements OnClickListener {

	/** Constant */

	/** View declare */
	private View mRootView;
	private LinearLayout mLayoutTempFormat;
	private LinearLayout mLayoutRefreshInterval;
	private TextView mTvewTempFormat;
	private TextView mTvewRefreshInterval;
	/** Objects */
	private String[] mArraysTempFormat;
	private String[] mArraysRefreshInterval;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.setting_fragment, container,
				false);
		return mRootView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.setting_layout_temperature_format: { // Temperature format
				showTempFormatDialog();
				break;
			}
			case R.id.setting_layout_refesh_interval: { // Refresh Interval
				showRefreshIntervalDialog();
				break;
			}
			default: { // Unknown resource id
				LogEx.d("Unknown resource id = " + v.getId());
				break;
			}
		}
	}

	protected void createViewsAndObject() {
		// create and bind the view
		mLayoutTempFormat = (LinearLayout) mRootView
				.findViewById(R.id.setting_layout_temperature_format);
		mLayoutRefreshInterval = (LinearLayout) mRootView
				.findViewById(R.id.setting_layout_refesh_interval);
		mTvewTempFormat = (TextView) mRootView
				.findViewById(R.id.setting_tvew_temperature_format_value);
		mTvewRefreshInterval = (TextView) mRootView
				.findViewById(R.id.setting_tvew_refresh_interval_value);

		// set click listener
		mLayoutTempFormat.setOnClickListener(this);
		mLayoutRefreshInterval.setOnClickListener(this);
	}

	protected void loadDataAndShown() {
		// set temperature format value
		mArraysTempFormat = getResources().getStringArray(
				R.array.temperature_format_values);
		String currentFormat = getTemperatureFormat();
		if (currentFormat == null) {
			currentFormat = "";
		}
		mTvewTempFormat.setText(currentFormat);
		// set refresh interval value
		mArraysRefreshInterval = getResources().getStringArray(
				R.array.update_frequency_values);
		String currentValues = "";
		if (mArraysRefreshInterval != null && mArraysRefreshInterval.length > 0) {
			currentValues = mArraysRefreshInterval[getFrequencyIndex()];
		}
		mTvewRefreshInterval.setText(currentValues);
	}

	private void showTempFormatDialog() {
		// show single choice dialog
		new AlertDialog.Builder(getActivity())
				.setTitle(
						getResources().getText(
								R.string.setting_temperature_format))
				.setItems(mArraysTempFormat,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// set Text shown and restore the choice
								if (which <= mArraysTempFormat.length) {
									mTvewTempFormat
											.setText(mArraysTempFormat[which]);
									PreferencesManager
											.setTemperatureFormat(mArraysTempFormat[which]);
								}
							}
						}).show();
	}

	private void showRefreshIntervalDialog() {
		// show single choice dialog
		new AlertDialog.Builder(getActivity())
				.setTitle(
						getResources().getText(
								R.string.setting_refresh_interval))
				.setItems(mArraysRefreshInterval,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// set Text shown and restore the choice
								if (which <= mArraysRefreshInterval.length) {
									mTvewRefreshInterval
											.setText(mArraysRefreshInterval[which]);
									PreferencesManager
											.setUpdateFrequency(GlobalConstant.Weather.FREQUENCY[which]);
								}
							}
						}).show();
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
