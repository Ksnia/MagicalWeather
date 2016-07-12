package com.bianaiqi.components;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.bianaiqi.ui.CloudLayout;
import com.bianaiqi.ui.FogLayout;
import com.bianaiqi.ui.RainLayout;
import com.bianaiqi.ui.SnowLayout;
import com.bianaiqi.ui.SunDayLayout;
import com.bianaiqi.ui.SunNightLayout;
import com.bianaiqi.ui.ThunderStormLayout;
import com.bianaiqi.util.RandomGenerator;

public class MainActivity extends Activity {

    private final int SUN_DAY = 0;
    private final int SUN_NIGHT = 1;
    private final int CLOUD = 2;
    private final int RAIN = 3;
    private final int THUNDERSTORM = 4;
    private final int SNOW = 5;
    private final int FOG = 6;
    private final int WEATHER_TYPE_COUNT = 7;

    private final int DEFAULT_WEATHTER_TYPE = 0;
    private View mWeatherView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ///M: Test for full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///M: Test for full Screen

        FrameLayout mWeatherLayout = (FrameLayout) findViewById(R.id.weather_bg);
        mWeatherView = getWeatherViewLayout(DEFAULT_WEATHTER_TYPE);
        mWeatherLayout.addView(mWeatherView);

        mWeatherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int i = (int) (Math.random() * WEATHER_TYPE_COUNT);
                RandomGenerator.generatorWeatherType();
                updateWeatherLayout(RandomGenerator.getWeatherType());
            }
        });
    }

    private void updateWeatherLayout(int type){
        ViewGroup parent = null;
        if(null != mWeatherView){
            parent = (ViewGroup) mWeatherView.getParent();
            parent.removeView(mWeatherView);
            mWeatherView = null;
        }

        if(null != parent){
            mWeatherView = getWeatherViewLayout(type);
            parent.addView(mWeatherView);

            mWeatherView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RandomGenerator.generatorWeatherType();
                    updateWeatherLayout(RandomGenerator.getWeatherType());
                }
            });
        }
    }

    private View getWeatherViewLayout(int type){
        View view;
        switch (type){
            case SUN_NIGHT:
                view = new SunNightLayout(getApplication());
                break;

            case CLOUD:
                view = new CloudLayout(getApplicationContext());
                break;

            case RAIN:
                view = new RainLayout(getApplicationContext());
                break;

            case THUNDERSTORM:
                view = new ThunderStormLayout(getApplication());
                break;

            case FOG:
                view = new FogLayout(getApplicationContext());
                break;

            case SNOW:
                view = new SnowLayout(getApplicationContext());
                break;

            case SUN_DAY:
            default:
                view = new SunDayLayout(getApplicationContext());
                break;
        }
        return view;
    }
}

