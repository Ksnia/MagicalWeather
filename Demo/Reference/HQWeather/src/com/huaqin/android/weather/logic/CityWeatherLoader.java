package com.huaqin.android.weather.logic;

import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.huaqin.android.weather.model.CityWeather;
import com.huaqin.android.weather.provider.WeatherUpdateService;
import com.huaqin.android.weather.util.DBService;
import com.huaqin.android.weather.util.GlobalConstant;
import com.huaqin.android.weather.util.WebClientUtil;
import com.huaqin.android.weather.util.XmlPullParserHelper;

public class CityWeatherLoader {

	private static final String URL_CITY_WEATHER_QUERY = "http://weather.yahooapis.com/forecastrss?w=%s&u=%s";
	private Context mContext;
	private String mStrCityName;
	private DBService mDb;

	public CityWeatherLoader(Context context) {
		this.mContext = context;
		this.mDb = new DBService(mContext);
	}

	public void excute(String[] strQuery, String strCityName) {
		mStrCityName = strCityName;
		if (strQuery != null && strQuery.length > 0
				&& strQuery[0].length() != 0) {
			WeatherTask task = new WeatherTask();
			task.execute(strQuery);
		}
	}

	class WeatherTask extends AsyncTask<String, Integer, CityWeather> {

		@Override
		protected CityWeather doInBackground(String... params) {
			if (params == null) {
				return null;
			}
			String url = String
					.format(URL_CITY_WEATHER_QUERY, (Object[]) params).trim()
					.replaceAll(" ", "%20");
			InputStream is = WebClientUtil.getInstance(mContext).query(url);
			CityWeather cityWeather = XmlPullParserHelper.getCityWeather(is,
					GlobalConstant.CODE_FORMAT);
			if (cityWeather != null) {
				// save the weather
				cityWeather.setCityName(mStrCityName);
				cityWeather.setWoeid(params[0]);
			}
			mDb.insertCityWeather(cityWeather);
			return cityWeather;
		}

		@Override
		protected void onPostExecute(CityWeather result) {
			super.onPostExecute(result);
			if (result != null) {
				// query the image resource
				/**
				 * if (result.getImgUrl() != null && result.getImgUrl().length()
				 * > 0) { ImageLoader loader = new ImageLoader(mContext);
				 * loader.excute(result.getImgUrl()); }
				 */
				// send the broadcast to notify the widget screen
				Intent intent = new Intent();
				intent.setAction(GlobalConstant.BroadCastAction.WEATHER_UPDATE_COMPLETE);
				intent.putExtra(GlobalConstant.IntentTag.INTENT_WEATHER_VALUE,
						result);
				mContext.getApplicationContext().sendBroadcast(intent);
			}
            // to tell service update complete
            Intent update = new Intent(mContext, WeatherUpdateService.class);
            update.setAction(GlobalConstant.IntentAction.ACTION_UPDATE_COMPLETE);
            mContext.startService(update);
		}
	}
}
