package com.bianaiqi.components;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.bianaiqi.util.MyLog;
import com.bianaiqi.WeatherLayoutManager;
import com.bianaiqi.WeatherManager;
import com.bianaiqi.weather.WeatherUtils;

public class MainActivity extends Activity {

    private FrameLayout mWeatherView;
    private WeatherLayoutManager mWeatherLayoutMgr;
    private WeatherManager mWeatherMgr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ///M: Test for full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///M: Test for full Screen

        /// init flow  1 parentView 2 WeatherUtils  3 LayoutMgr  4 WeatherMgr 5 ClockView
        // 1
        mWeatherView = (FrameLayout) findViewById(R.id.weather_bg);
        // 2
        WeatherUtils.init(getApplicationContext());
        // 3
        mWeatherLayoutMgr = WeatherLayoutManager.getInstance();
        mWeatherLayoutMgr.init(getApplicationContext(),mWeatherView);
        // 4
        mWeatherMgr = WeatherManager.getInstance();
        mWeatherMgr.addListener(mWeatherLayoutMgr);

        mWeatherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLog.d(MainActivity.class,"mWeatherView  Click");
                mWeatherMgr.requestWeatherData();
            }
        });
    }
}

