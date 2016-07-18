package com.bianaiqi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bianaiqi.components.R;
import com.bianaiqi.manager.WeatherLayoutManager;
import com.bianaiqi.manager.WeatherManager;
import com.bianaiqi.util.MyLog;
import com.bianaiqi.weather.WeatherUtils;
import com.bianaiqi.weather.data.local.WeatherDataItem;

public class MainActivity extends Activity implements WeatherManager.WeatherDataUpdateListener {

    private FrameLayout mWeatherView;
    private WeatherLayoutManager mWeatherLayoutMgr;
    private WeatherManager mWeatherMgr;

    private TextView mTvWeekDay;
    private TextView mTvDate;
    private TextView mTvWeather;
    private TextView mTvTemp;
    private TextView mTvTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ///M: Test for full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///M: Test for full Screen

        mTvWeekDay = (TextView) findViewById(R.id.weekday);
        mTvDate = (TextView) findViewById(R.id.date);
        mTvWeather = (TextView) findViewById(R.id.weather);
        mTvTemp = (TextView) findViewById(R.id.tempareture);
        mTvTips = (TextView) findViewById(R.id.tips);


        /// init flow  1 parentView  2 ClockView 3 LayoutMgr  4 WeatherMgr
        // 1
        mWeatherView = (FrameLayout) findViewById(R.id.weather_bg);
        // 3
        mWeatherLayoutMgr = WeatherLayoutManager.getInstance();
        mWeatherLayoutMgr.init(getApplicationContext(), mWeatherView);
        // 4
        mWeatherMgr = WeatherManager.getInstance();
        mWeatherMgr.setLayoutUpdateListener(mWeatherLayoutMgr);
        mWeatherMgr.setDataUpdateListener(this);

        mWeatherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLog.d(MainActivity.class, "mWeatherView  Click");
                mWeatherMgr.requestWeatherData(getApplicationContext(),
                        WeatherUtils.getDefaultEngineType(),
                        WeatherUtils.getTestCity(getApplicationContext()),
                        WeatherUtils.getDefaultRequestType());
            }
        });
    }

    @Override
    public void onWeatherDataUpdate(WeatherDataItem data) {
        MyLog.d(MainActivity.class, "onWeatherDataUpdate");
        if (null == data) {
            mTvTips.setText(getApplicationContext().getResources().getString(R.string.weather_update_fail));
        } else {
            int temp = data.getCurTemp();
            String weather = data.getWeather();
            String str_date = data.getDate().toString();
            String tips = data.getCity().getName();
            mTvWeekDay.setText(str_date);
            mTvDate.setText("");
            mTvWeather.setText(weather);
            mTvTemp.setText(temp + "'");
            mTvTips.setText(tips);
        }
    }
}