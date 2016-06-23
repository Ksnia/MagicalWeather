package com.huaqin.android.weather.provider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.RemoteViews;

import com.huaqin.android.weather.R;
import com.huaqin.android.weather.model.Weather;
import com.huaqin.android.weather.model.WeatherType;
import com.huaqin.android.weather.ui.SettingsActivity;
import com.huaqin.android.weather.util.DBService;
import com.huaqin.android.weather.util.GlobalConstant;
import com.huaqin.android.weather.util.LogEx;
import com.huaqin.android.weather.util.PreferencesManager;

public class WeatherWidgetProvider extends AppWidgetProvider {

	private DBService mDb;

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		Context app_ctx = context.getApplicationContext();
		app_ctx.registerReceiver(this, new IntentFilter(
				Intent.ACTION_BOOT_COMPLETED));
		app_ctx.registerReceiver(this, new IntentFilter(
				Intent.ACTION_TIME_CHANGED));
		app_ctx.registerReceiver(this, new IntentFilter(
				Intent.ACTION_TIMEZONE_CHANGED));
		app_ctx.registerReceiver(this,
				new IntentFilter(Intent.ACTION_TIME_TICK));
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);

		Log.d(GlobalConstant.TAG, "action = " + intent.getAction());
		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			// starta update service
			Intent service = new Intent(context, WeatherUpdateService.class);
			service.setAction(GlobalConstant.IntentAction.ACTION_FREQUENCY_UPDATE);
			context.startService(service);
		}
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		// receive the city name
		if (GlobalConstant.BroadCastAction.WEATHER_UPDATE_CITY.equals(intent
				.getAction())) {
			// receive the city weather information
			updateWeather(context, appWidgetManager,
					appWidgetManager.getAppWidgetIds(getComponentName(context)));
			return;
		} else if (GlobalConstant.BroadCastAction.WEATHER_UPDATE_COMPLETE
				.equals(intent.getAction())) {
			// receive the image URI
			updateWeather(context, appWidgetManager,
					appWidgetManager.getAppWidgetIds(getComponentName(context)));
			return;
		} else if (GlobalConstant.BroadCastAction.WEATHER_UPDATE_IMGAE
				.equals(intent.getAction())) {
			// receive the temperature changed
			return;
		} else if (GlobalConstant.BroadCastAction.WEATHER_UPDATE_TEMP_FORMAT
				.equals(intent.getAction())) {
			updateWeather(context, appWidgetManager,
					appWidgetManager.getAppWidgetIds(getComponentName(context)));
			return;
		} else if (Intent.ACTION_TIME_CHANGED.equals(intent.getAction())
				|| Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
			updateWeather(context, appWidgetManager,
					appWidgetManager.getAppWidgetIds(getComponentName(context)));
		}
		// update time
		updateDateTime(context, appWidgetManager,
				appWidgetManager.getAppWidgetIds(getComponentName(context)));
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		updateDateTime(context, appWidgetManager, appWidgetIds);
		updateWeather(context, appWidgetManager, appWidgetIds);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	private void updateDateTime(Context context,
			AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// update the widget
		for (int i = 0; i < appWidgetIds.length; i++) {
			// initialize time view
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.weather_appwidget);

			// set the whole date
			SimpleDateFormat sdfDateFormat = new SimpleDateFormat(
					GlobalConstant.Time.TIME_DAY_FULL);
			String day = sdfDateFormat.format(new Date());
			remoteViews.setTextViewText(R.id.time_weekday, day);
			final Calendar now = Calendar.getInstance();
			remoteViews.setTextViewText(R.id.time_date,
					DateFormat.getTimeFormat(context).format(now.getTime()));

			// click to setting screen
			Intent intentSetting = new Intent(context, SettingsActivity.class);
			if (false) {// FeatureOption.HQ_WEATHER_NEW_LAYOUT) {
				remoteViews.setOnClickPendingIntent(R.id.weather_city,
						PendingIntent.getActivity(context, 0, intentSetting,
								PendingIntent.FLAG_UPDATE_CURRENT));
			} else {
				remoteViews.setOnClickPendingIntent(R.id.weather_view,
						PendingIntent.getActivity(context, 0, intentSetting,
								PendingIntent.FLAG_UPDATE_CURRENT));
			}
			remoteViews.setOnClickPendingIntent(R.id.widget_background,
					PendingIntent.getActivity(context, 0, intentSetting,
							PendingIntent.FLAG_UPDATE_CURRENT));

			// click to update weather
			Intent update = new Intent(context, WeatherUpdateService.class);
			update.setAction(GlobalConstant.IntentAction.ACTION_UPDATE);
			remoteViews.setOnClickPendingIntent(R.id.weather_lyout_refresh,
					PendingIntent.getService(context, 0, update,
							PendingIntent.FLAG_UPDATE_CURRENT));

			// set pendingIntent
			Intent intent = new Intent();
			intent.setClassName("com.android.deskclock",
					"com.android.deskclock.DeskClock");

			remoteViews.setOnClickPendingIntent(R.id.time_weekday,
					PendingIntent.getActivity(context, 0, intent,
							PendingIntent.FLAG_UPDATE_CURRENT));
			remoteViews.setOnClickPendingIntent(R.id.time_date, PendingIntent
					.getActivity(context, 0, intent,
							PendingIntent.FLAG_UPDATE_CURRENT));
			// update AppWidget
			appWidgetManager.updateAppWidget(new ComponentName(context,
					WeatherWidgetProvider.class), remoteViews);
		}
	}

	private void updateWeather(Context context,
			AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		mDb = new DBService(context);
		// update the widget
		for (int i = 0; i < appWidgetIds.length; i++) {
			// initialize time view
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.weather_appwidget);

			// update the screen
			if ("".equals(PreferencesManager.getCity())) {
				remoteViews.setTextViewText(R.id.weather_city,
						context.getString(R.string.setting_hint));
			} else {
				remoteViews.setTextViewText(R.id.weather_city,
						PreferencesManager.getCity());
			}

			// set weather forecast
			Intent service = new Intent(context, WeatherWidgetService.class);
			remoteViews.setRemoteAdapter(appWidgetIds[i],
					R.id.weather_gver_forecast, service);

			// set weather information
			if (mDb != null && PreferencesManager.getWoeid() != null
					&& PreferencesManager.getWoeid().length() > 0) {
				Locale.setDefault(Locale.ENGLISH);
				Date date = new Date();
				String strDate = DateFormat.format(
						GlobalConstant.Time.TIME_FORMAT, date).toString();
				Weather weather = mDb.queryCityWeatherDay(
						PreferencesManager.getWoeid(), strDate);
				if (weather != null) {
					LogEx.d("weather format = " + weather.getTempFormat());
					// transform to ℉/℃
					StringBuffer sb = new StringBuffer();
					if (PreferencesManager.getTemperatureFormat().equals(
							weather.getTempFormat())) {
						sb.append(weather.getLowTemp());
						sb.append("~");
						sb.append(weather.getHighTemp());
					} else {
						int itempLow = 0;
						int itempHigh = 0;
						// Degrees Fahrenheit
						if (GlobalConstant.Weather.WEATHER_TEMPERATURE_FORMAT_F
								.equals(weather.getTempFormat())) {
							itempLow = (int) ((weather.getLowTemp() - 32) / 1.8);
							itempHigh = (int) ((weather.getHighTemp() - 32) / 1.8);
						} else {
							itempLow = (int) (32 + 1.8 * weather.getLowTemp());
							itempHigh = (int) (32 + 1.8 * weather.getHighTemp());
						}
						sb.append(itempLow);
						sb.append("~");
						sb.append(itempHigh);
					}
					sb.append(PreferencesManager.getTemperatureFormat());
					// set temperature
					remoteViews.setTextViewText(R.id.weather_temperature,
							sb.toString());
					remoteViews.setTextViewText(R.id.weather_temperature_state,
							weather.getText());
					// set last update time
					if (weather.getPubDate() != null) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								GlobalConstant.Time.TIME_REFRESH_FORMAT);
						String updateTime = "";
						try {
							int index = weather.getPubDate().lastIndexOf(" ");
							String strTar = weather.getPubDate().substring(0,
									index);
							updateTime = sdf.format(new Date(strTar));
						} catch (Exception e) {
							// if format error use current date
							updateTime = sdf.format(new Date());
						}
						remoteViews.setTextViewText(R.id.weather_update_date,
								updateTime);
					}
					// update the weather image
					if (false) {
						// FeatureOption.HQ_WEATHER_NEW_LAYOUT) {
						remoteViews.setInt(R.id.widget_background,
								"setBackgroundResource",
								WeatherType.getWeatherIcon(weather.getCode()));
					} else {
						remoteViews.setImageViewResource(R.id.weather_view,
								WeatherType.getWeatherIcon(weather.getCode()));
					}
				}
			} else {
				// set temperature
				remoteViews.setTextViewText(R.id.weather_temperature, "");
				remoteViews.setTextViewText(R.id.weather_temperature_state, "");
				// set last update time
				remoteViews.setTextViewText(R.id.weather_update_date, "");
				// update the weather image
				if (false) {// FeatureOption.HQ_WEATHER_NEW_LAYOUT) {
					remoteViews.setInt(R.id.widget_background,
							"setBackgroundResource",
							WeatherType.getWeatherIcon(3200));
				} else {
					remoteViews.setImageViewResource(R.id.weather_view,
							WeatherType.getWeatherIcon(3200));
				}
			}
			// update AppWidget
			appWidgetManager.updateAppWidget(new ComponentName(context,
					WeatherWidgetProvider.class), remoteViews);
			appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[i],
					R.id.weather_gver_forecast);
		}
	}

	/**
	 * private void updateTime(Context context, AppWidgetManager
	 * appWidgetManager, int[] appWidgetIds) {
	 * 
	 * Log.d(GlobalConstant.TAG, "updateTime appWidgetIds.length = " +
	 * appWidgetIds.length);
	 * 
	 * mDb = new DBService(context); // update the widget for (int i = 0; i <
	 * appWidgetIds.length; i++) { int am_pm = 0; // initialize time view
	 * RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
	 * R.layout.weather_appwidget);
	 * 
	 * // set the whole date SimpleDateFormat sdfDateFormat = new
	 * SimpleDateFormat( GlobalConstant.Time.TIME_DAY_FULL); String day =
	 * sdfDateFormat.format(new Date());
	 * remoteViews.setTextViewText(R.id.time_weekday, day); SimpleDateFormat
	 * format = new SimpleDateFormat( GlobalConstant.Time.TIME_HM);
	 * remoteViews.setTextViewText(R.id.time_date, format.format(new Date()));
	 * // set hour mode if (!get24HourMode(context)) { Calendar calendar =
	 * Calendar.getInstance(); am_pm = calendar.get(Calendar.AM_PM); if (am_pm
	 * == 1) { remoteViews.setTextViewText(R.id.time_apm,
	 * GlobalConstant.Time.TIME_PM); } else {
	 * remoteViews.setTextViewText(R.id.time_apm, GlobalConstant.Time.TIME_AM);
	 * } }
	 * 
	 * // update the screen if ("".equals(PreferencesManager.getCity())) {
	 * remoteViews.setTextViewText(R.id.weather_city,
	 * context.getString(R.string.setting_hint)); } else {
	 * remoteViews.setTextViewText(R.id.weather_city,
	 * PreferencesManager.getCity()); } if (mDb != null &&
	 * PreferencesManager.getWoeid() != null &&
	 * PreferencesManager.getWoeid().length() > 0) {
	 * Locale.setDefault(Locale.ENGLISH); Date date = new Date(); String strDate
	 * = DateFormat.format( GlobalConstant.Time.TIME_FORMAT, date).toString();
	 * Weather weather = mDb.queryCityWeatherDay( PreferencesManager.getWoeid(),
	 * strDate); if (weather != null) { LogEx.d("weather format = " +
	 * weather.getTempFormat()); // transform to ℉/℃ StringBuffer sb = new
	 * StringBuffer(); if (PreferencesManager.getTemperatureFormat().equals(
	 * weather.getTempFormat())) { sb.append(weather.getLowTemp());
	 * sb.append("~"); sb.append(weather.getHighTemp()); } else { int itempLow =
	 * 0; int itempHigh = 0; // Degrees Fahrenheit if
	 * (GlobalConstant.Weather.WEATHER_TEMPERATURE_FORMAT_F
	 * .equals(weather.getTempFormat())) { itempLow = (int)
	 * ((weather.getLowTemp() - 32) / 1.8); itempHigh = (int)
	 * ((weather.getHighTemp() - 32) / 1.8); } else { itempLow = (int) (32 + 1.8
	 * * weather.getLowTemp()); itempHigh = (int) (32 + 1.8 *
	 * weather.getHighTemp()); } sb.append(itempLow); sb.append("~");
	 * sb.append(itempHigh); }
	 * sb.append(PreferencesManager.getTemperatureFormat()); // set temperature
	 * remoteViews.setTextViewText(R.id.weather_temperature, sb.toString());
	 * remoteViews.setTextViewText(R.id.weather_temperature_state,
	 * weather.getText()); // set last update time if (weather.getPubDate() !=
	 * null) { SimpleDateFormat sdf = new SimpleDateFormat(
	 * GlobalConstant.Time.TIME_REFRESH_FORMAT); String updateTime = ""; try {
	 * int index = weather.getPubDate().lastIndexOf(" "); String strTar =
	 * weather.getPubDate().substring(0, index); updateTime = sdf.format(new
	 * Date(strTar)); } catch (Exception e) { // if format error use current
	 * date updateTime = sdf.format(new Date()); }
	 * remoteViews.setTextViewText(R.id.weather_update_date, updateTime); } //
	 * update the weather image if (false) { //
	 * FeatureOption.HQ_WEATHER_NEW_LAYOUT) {
	 * remoteViews.setInt(R.id.widget_background, "setBackgroundResource",
	 * WeatherType.getWeatherIcon(weather.getCode())); } else {
	 * remoteViews.setImageViewResource(R.id.weather_view,
	 * WeatherType.getWeatherIcon(weather.getCode())); } } } else { // set
	 * temperature remoteViews.setTextViewText(R.id.weather_temperature, "");
	 * remoteViews.setTextViewText(R.id.weather_temperature_state, ""); // set
	 * last update time remoteViews.setTextViewText(R.id.weather_update_date,
	 * ""); // update the weather image if (false) {//
	 * FeatureOption.HQ_WEATHER_NEW_LAYOUT) {
	 * remoteViews.setInt(R.id.widget_background, "setBackgroundResource",
	 * WeatherType.getWeatherIcon(3200)); } else {
	 * remoteViews.setImageViewResource(R.id.weather_view,
	 * WeatherType.getWeatherIcon(3200)); } }
	 * 
	 * // click to setting screen Intent intentSetting = new Intent(context,
	 * SettingsActivity.class); if (false) {//
	 * FeatureOption.HQ_WEATHER_NEW_LAYOUT) {
	 * remoteViews.setOnClickPendingIntent(R.id.weather_city,
	 * PendingIntent.getActivity(context, 0, intentSetting,
	 * PendingIntent.FLAG_UPDATE_CURRENT)); } else {
	 * remoteViews.setOnClickPendingIntent(R.id.weather_view,
	 * PendingIntent.getActivity(context, 0, intentSetting,
	 * PendingIntent.FLAG_UPDATE_CURRENT)); }
	 * remoteViews.setOnClickPendingIntent(R.id.widget_background,
	 * PendingIntent.getActivity(context, 0, intentSetting,
	 * PendingIntent.FLAG_UPDATE_CURRENT));
	 * 
	 * // click to update weather Intent update = new Intent(context,
	 * WeatherUpdateService.class);
	 * update.setAction(GlobalConstant.IntentAction.ACTION_UPDATE);
	 * remoteViews.setOnClickPendingIntent(R.id.weather_lyout_refresh,
	 * PendingIntent.getService(context, 0, update,
	 * PendingIntent.FLAG_UPDATE_CURRENT));
	 * 
	 * // set pendingIntent Intent intent = new Intent();
	 * intent.setClassName("com.android.deskclock",
	 * "com.android.deskclock.DeskClock");
	 * 
	 * remoteViews.setOnClickPendingIntent(R.id.time_weekday,
	 * PendingIntent.getActivity(context, 0, intent,
	 * PendingIntent.FLAG_UPDATE_CURRENT));
	 * remoteViews.setOnClickPendingIntent(R.id.time_date, PendingIntent
	 * .getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
	 * remoteViews.setOnClickPendingIntent(R.id.time_apm, PendingIntent
	 * .getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
	 * 
	 * // set weather forecast Intent service = new Intent(context,
	 * WeatherWidgetService.class);
	 * remoteViews.setRemoteAdapter(appWidgetIds[i], R.id.weather_gver_forecast,
	 * service);
	 * 
	 * // update AppWidget appWidgetManager.updateAppWidget(new
	 * ComponentName(context, WeatherWidgetProvider.class), remoteViews); } }
	 */

	static boolean get24HourMode(Context context) {
		return android.text.format.DateFormat.is24HourFormat(context);
	}

	static ComponentName getComponentName(Context context) {
		return new ComponentName(context, WeatherWidgetProvider.class);
	}
}
