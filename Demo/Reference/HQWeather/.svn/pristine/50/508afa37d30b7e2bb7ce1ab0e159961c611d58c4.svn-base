package com.huaqin.android.weather.provider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.huaqin.android.weather.R;
import com.huaqin.android.weather.model.Weather;
import com.huaqin.android.weather.model.WeatherType;
import com.huaqin.android.weather.util.DBService;
import com.huaqin.android.weather.util.GlobalConstant;
import com.huaqin.android.weather.util.LogEx;
import com.huaqin.android.weather.util.PreferencesManager;

/**
 * @ClassName:WeatherWidgetService
 * @Description:Forecast weather GridView adapter load class
 * @author:BingWu.Lee
 * @version 1.0
 * @date:2014-4-25
 */
public class WeatherWidgetService extends RemoteViewsService {

	private static final int COUNT = 5;

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		return new WeatherRemoteViewsFactory(this.getApplicationContext(),
				intent);
	}
	
    /**
     * @ClassName:WeatherRemoteViewsFactory
     * @Description:Forecast weather GridView adapter load class
     * @author:BingWu.Lee
     * @version 1.0
     * @date:2014-4-25
     */
	class WeatherRemoteViewsFactory implements
			RemoteViewsService.RemoteViewsFactory {

		private Context mContext;
		private List<Weather> mListWeather;
		private DBService mDb;

		public WeatherRemoteViewsFactory(Context context, Intent intent) {
			mContext = context;
			mDb = new DBService(context);
		}

		@Override
		public void onCreate() {
			doLoad();
		}

		@Override
		public RemoteViews getViewAt(int position) {
			RemoteViews rv = new RemoteViews(mContext.getPackageName(),
					R.layout.forecast_view);
			if (mListWeather != null && mListWeather.size() > position) {
				rv.setTextViewText(R.id.forecast_weekday,
						mListWeather.get(position).getDay());
				rv.setImageViewResource(R.id.forecast_weather_view, WeatherType
						.getWeatherIcon(mListWeather.get(position).getCode()));
				// transform to ℉/℃
				StringBuffer sb = new StringBuffer();
				if (PreferencesManager.getTemperatureFormat().equals(
						mListWeather.get(position).getTempFormat())) {
					sb.append(mListWeather.get(position).getLowTemp());
					sb.append("~");
					sb.append(mListWeather.get(position).getHighTemp());
				} else {
					int itempLow = 0;
					int itempHigh = 0;
					// Degrees Fahrenheit
					if (GlobalConstant.Weather.WEATHER_TEMPERATURE_FORMAT_F
							.equals(mListWeather.get(position).getTempFormat())) {
						itempLow = (int) ((mListWeather.get(position)
								.getLowTemp() - 32) / 1.8);
						itempHigh = (int) ((mListWeather.get(position)
								.getHighTemp() - 32) / 1.8);
					} else {
						itempLow = (int) (32 + 1.8 * mListWeather.get(position)
								.getLowTemp());
						itempHigh = (int) (32 + 1.8 * mListWeather
								.get(position).getHighTemp());
					}
					sb.append(itempLow);
					sb.append("~");
					sb.append(itempHigh);
				}
				sb.append(PreferencesManager.getTemperatureFormat());
				// set temperature
				rv.setTextViewText(R.id.forecast_weather_temperature, sb.toString());
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_WEEK, position);
				SimpleDateFormat sdfDateFormat = new SimpleDateFormat(
						GlobalConstant.Time.TIME_DAY_SKELETON);
				String day = sdfDateFormat.format(calendar.getTime());
				calendar.clear();
				rv.setTextViewText(R.id.forecast_weekday, day);
				rv.setImageViewResource(R.id.forecast_weather_view,
						WeatherType.getWeatherIcon(3200));
				rv.setTextViewText(R.id.forecast_weather_temperature, "N/A");
			}
			return rv;
		}

		@Override
		public int getCount() {
			return COUNT;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public RemoteViews getLoadingView() {
			return null;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public void onDataSetChanged() {
			doLoad();
		}

		@Override
		public void onDestroy() {

		}

		private void doLoad() {
			mListWeather = mDb.queryCityWeathers(PreferencesManager.getWoeid());
			LogEx.d("mListWeather.size = " + mListWeather.size());
		}
	}
}
