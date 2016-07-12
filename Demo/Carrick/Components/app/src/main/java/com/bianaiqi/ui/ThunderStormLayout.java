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
public class ThunderStormLayout extends WeatherLayout{
    private View rootView;

    public ThunderStormLayout(Context context) {
        super(context);
        onViewInit();
    }

    public ThunderStormLayout(Context context, AttributeSet attrs) {
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
        rootView = inflate(getContext(), R.layout.thunderstorm_layout, this);

        mWeatherIcon = (ImageView) findViewById(R.id.weather_icon);
        showWeatherIcon();
    }
}
