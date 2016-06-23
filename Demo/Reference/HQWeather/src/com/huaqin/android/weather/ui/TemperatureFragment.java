package com.huaqin.android.weather.ui;

import java.util.Date;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huaqin.android.weather.R;
import com.huaqin.android.weather.model.Weather;
import com.huaqin.android.weather.model.WeatherType;
import com.huaqin.android.weather.util.DBService;
import com.huaqin.android.weather.util.GlobalConstant;
import com.huaqin.android.weather.util.LogEx;
import com.huaqin.android.weather.util.PreferencesManager;
import com.huaqin.android.weather.util.ToastManager;

/**
 * The temperature shown screen
 * 
 * @author:MG.Rong
 * @version:1.0
 * @Date:2013-8-14
 */
public class TemperatureFragment extends CommonFragment {

	/** Root view */
	private View mRootView;
	/** The shown view */
	private TextView mTvewTemp;
	private TextView mTvewText;
	private TextView mTvewHighTemp;
	private TextView mTvewLowTemp;
	private ImageView mIvewWeather;
	private TextView mTvewSunrise;
	private TextView mTvewSunset;
	private TextView mTvewHumidity;
	private TextView mTvewPressure;
	private TextView mTvewPubDate;
	/** Database instance */
	private DBService mDb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.temperature_fragment, container,
				false);
		return mRootView;
	}

	@Override
	protected void createViewsAndObject() {
		// create and bind the view
		mTvewTemp = (TextView) mRootView
				.findViewById(R.id.temperature_tvew_current_temp);
		mTvewText = (TextView) mRootView
				.findViewById(R.id.temperature_tvew_current_text);
		mTvewHighTemp = (TextView) mRootView
				.findViewById(R.id.temperature_tvew_high_temp);
		mTvewLowTemp = (TextView) mRootView
				.findViewById(R.id.temperature_tvew_low_temp);
		mIvewWeather = (ImageView) mRootView
				.findViewById(R.id.temperature_ivew_weather);
		// detail information view
		mTvewSunrise = (TextView) mRootView
				.findViewById(R.id.temperature_tvew_sunrise);
		mTvewSunset = (TextView) mRootView
				.findViewById(R.id.temperature_tvew_sunset);
		mTvewHumidity = (TextView) mRootView
				.findViewById(R.id.temperature_tvew_humidity);
		mTvewPressure = (TextView) mRootView
				.findViewById(R.id.temperature_tvew_pressure);
		mTvewPubDate = (TextView) mRootView
				.findViewById(R.id.temperature_tvew_pubdate);
		// create instance
		mDb = new DBService(getActivity());
	}

	@Override
	protected void loadDataAndShown() {
		loadDataAndShown(PreferencesManager.getWoeid());
	}

	private void loadDataAndShown(String strWoeid) {
		if (mDb != null && PreferencesManager.getWoeid() != null
				&& PreferencesManager.getWoeid().length() > 0) {
			// get date format
			Date date = new Date();
			String strDate = DateFormat.format(GlobalConstant.Time.TIME_FORMAT,
					date).toString();
			// to make sure the woeid is effective
			if (strWoeid == null || strWoeid.length() == 0) {
				ToastManager
						.makeToast(GlobalConstant.Toast.TOAST_NOT_INITILIZE);
				return;
			}
			// query the weather information
			Weather weather = mDb.queryCityWeatherDay(strWoeid, strDate);
			if (weather != null) {
				LogEx.d("weather format = " + weather.getTempFormat());
				// transform to ℉/℃
				StringBuffer sb = null;
				if (PreferencesManager.getTemperatureFormat().equals(
						weather.getTempFormat())) {
					// set current temperature
					sb = new StringBuffer();
					sb.append(weather.getHighTemp());
					sb.append(PreferencesManager.getTemperatureFormat());
					mTvewTemp.setText(sb.toString());
					// set high temperature
					sb = new StringBuffer();
					sb.append(weather.getHighTemp());
					sb.append(PreferencesManager.getTemperatureFormat());
					mTvewHighTemp.setText(sb.toString());
					// set low temperature
					sb = new StringBuffer();
					sb.append(weather.getLowTemp());
					sb.append(PreferencesManager.getTemperatureFormat());
					mTvewLowTemp.setText(sb.toString());
				} else {
					int itempLow = 0;
					int itempHigh = 0;
					String strTempFormat = "";
					// Degrees Fahrenheit
					if (GlobalConstant.Weather.WEATHER_TEMPERATURE_F
							.equals(PreferencesManager.getTemperatureFormat())) {
						LogEx.d("WEATHER_TEMPERATURE_F");
						itempLow = (int) (32 + 1.8 * weather.getLowTemp());
						itempHigh = (int) (32 + 1.8 * weather.getHighTemp());
						strTempFormat = GlobalConstant.Weather.WEATHER_TEMPERATURE_FORMAT_F;
					} else {
						LogEx.d("WEATHER_TEMPERATURE_C");
						itempLow = (int) ((weather.getLowTemp() - 32) / 1.8);
						itempHigh = (int) ((weather.getHighTemp() - 32) / 1.8);
						strTempFormat = GlobalConstant.Weather.WEATHER_TEMPERATURE_FORMAT_C;
					}
					// set current temperature
					sb = new StringBuffer();
					sb.append(itempHigh);
					sb.append(strTempFormat);
					mTvewTemp.setText(sb.toString());
					// set high temperature
					sb = new StringBuffer();
					sb.append(itempHigh);
					sb.append(strTempFormat);
					mTvewHighTemp.setText(sb.toString());
					// set low temperature
					sb = new StringBuffer();
					sb.append(itempLow);
					sb.append(strTempFormat);
					mTvewLowTemp.setText(sb.toString());
				}
				// set temperature text
				mTvewText.setText(weather.getText());
				// update the weather image
				mIvewWeather.setImageResource(WeatherType
						.getWeatherIcon(weather.getCode()));

				// set detail information
				if (weather.getHumidity() != null) {
					mTvewHumidity.setText(weather.getHumidity().concat("%"));
				}
				if (weather.getPressure() != null) {
					mTvewPressure.setText(weather.getPressure().concat("mb"));
				}
				mTvewSunrise.setText(weather.getSunrise());
				mTvewSunset.setText(weather.getSunset());
				mTvewPubDate.setText(weather.getPubDate());
			}
		}
	}
}
