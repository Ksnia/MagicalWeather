package com.bianaiqi.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bianaiqi.components.R;

/**
 * Created by Carrick on 2016/7/11.
 */
public class SnowLayout extends WeatherLayout {
    private View rootView;

    public SnowLayout(Context context) {
        super(context);
        onViewInit();
    }

    public SnowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        onViewInit();
    }

    @Override
    public void startAnimation() {

    }

    @Override
    public void stopAnimation() {

    }

    private void onViewInit(){
        rootView = inflate(getContext(), R.layout.snow_layout, this);

        mWeatherIcon = (ImageView) findViewById(R.id.weather_icon);
        showWeatherIcon();
    }
}
