package com.huaqin.android.weather;
import java.io.File;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.huaqin.android.weather.provider.WeatherUpdateService;
import com.huaqin.android.weather.util.DBService;
import com.huaqin.android.weather.util.DeviceParams;
import com.huaqin.android.weather.util.GlobalConstant;
import com.huaqin.android.weather.util.ToastManager;
import com.huaqin.android.weather.util.PreferencesManager;
import com.huaqin.android.weather.util.WebClientUtil;

/**
 * @Description:The main application of Weather3d,which is display the date and
 *                  the weather
 * @author MG.Rong
 * @Date:2013/08/05
 */
public class WeatherApplication extends Application {

    private File mCatch;
    private AlarmManager mAlarmManger;
    private PendingIntent mService;

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize
        WebClientUtil.getInstance(getApplicationContext());
        ToastManager.getInstance(getApplicationContext());
        PreferencesManager.getInstance(getApplicationContext());
        DeviceParams.getInstance(getApplicationContext());
        // create the database
        new DBService(getApplicationContext());
        // initialize alarmManger
        mAlarmManger = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // calcel alarm
        calcelAlarm();
    }

    public File getCatch() {
        if (mCatch == null) {
            mCatch = new File(Environment.getExternalStorageDirectory(),
                    GlobalConstant.File.FILE_CATCH);
            if (!mCatch.exists()) {
                mCatch.mkdirs();
            }
        }
        return mCatch;
    }

    public AlarmManager getAlarmManager() {
        return mAlarmManger;
    }

    public void setRepeating(int times) {
        if (mAlarmManger != null) {
            mAlarmManger.cancel(getPendingIntent());
            mAlarmManger.setRepeating(AlarmManager.RTC,
                    System.currentTimeMillis() + times, times,
                    getPendingIntent());
        }
    }

    public void calcelAlarm() {
        if (mAlarmManger != null) {
            mAlarmManger.cancel(getPendingIntent());
        }
    }

    private PendingIntent getPendingIntent() {
        if (mService == null) {
            Intent intent = new Intent(getApplicationContext(),
                    WeatherUpdateService.class);
            intent.setAction(GlobalConstant.IntentAction.ACTION_UPDATE);
            mService = PendingIntent.getService(getApplicationContext(), 0,
                    intent, 0);
        }
        return mService;
    }
}
