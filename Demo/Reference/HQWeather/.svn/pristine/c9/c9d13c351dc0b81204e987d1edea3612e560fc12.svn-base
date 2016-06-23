package com.huaqin.android.weather.provider;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.widget.RemoteViews;

import com.huaqin.android.weather.R;
import com.huaqin.android.weather.WeatherApplication;
import com.huaqin.android.weather.logic.CityWeatherLoader;
import com.huaqin.android.weather.util.GlobalConstant;
import com.huaqin.android.weather.util.LogEx;
import com.huaqin.android.weather.util.PreferencesManager;

public class WeatherUpdateService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();

            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(getApplicationContext());
            RemoteViews views = new RemoteViews(this.getPackageName(),
                    R.layout.weather_appwidget);

            // update
            if (GlobalConstant.IntentAction.ACTION_UPDATE.equals(action)) {
                showProgressBar(appWidgetManager, views);
                update();
                // start AlarmTask
                startAlarmTask();
            } else if (GlobalConstant.IntentAction.ACTION_UPDATE_COMPLETE
                    .equals(action)) {
                hideProgressBar(appWidgetManager, views);
            } else if (GlobalConstant.IntentAction.ACTION_FREQUENCY_UPDATE
                    .equals(action)) {
                // start AlarmTask
                startAlarmTask();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startAlarmTask() {
        WeatherApplication appliacation = (WeatherApplication) this
                .getApplication();
        if (appliacation != null) {
            if (PreferencesManager.getUpdateFrequency() != -1
                    && PreferencesManager.getWoeid().length() != 0) {
                LogEx.d("update frequency = "
                        + PreferencesManager.getUpdateFrequency());
                int period = PreferencesManager.getUpdateFrequency() * 60 * 60 * 1000;
                LogEx.d("update schedule start and update period = " + period);
                // Task to circle update weather
                appliacation.setRepeating(period);
            } else {
                // not update
                appliacation.calcelAlarm();
            }
        }
    }

    private void showProgressBar(AppWidgetManager appWidgetManager,
            RemoteViews views) {
        int[] appWidgetIds = appWidgetManager
                .getAppWidgetIds(new ComponentName(getApplicationContext(),
                        WeatherWidgetProvider.class));
        for (int appWidgetId : appWidgetIds) {
            views.setViewVisibility(R.id.weather_lyout_refresh, View.GONE);
            views.setViewVisibility(R.id.weather_progressbar, View.VISIBLE);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private void hideProgressBar(AppWidgetManager appWidgetManager,
            RemoteViews views) {
        int[] appWidgetIds = appWidgetManager
                .getAppWidgetIds(new ComponentName(getApplicationContext(),
                        WeatherWidgetProvider.class));
        for (int appWidgetId : appWidgetIds) {
            views.setViewVisibility(R.id.weather_progressbar, View.GONE);
            views.setViewVisibility(R.id.weather_lyout_refresh, View.VISIBLE);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private void update() {
        LogEx.d("start update...............");
        CityWeatherLoader loader = new CityWeatherLoader(this);
        String[] strQuery = {
                PreferencesManager.getWoeid(),
                PreferencesManager.getTemperatureFormat().equals(
                        GlobalConstant.Weather.WEATHER_TEMPERATURE_FORMAT_C) ? GlobalConstant.Weather.WEATHER_TEMPERATURE_C
                        : GlobalConstant.Weather.WEATHER_TEMPERATURE_F };
        loader.excute(strQuery, PreferencesManager.getCity());
    }

}
