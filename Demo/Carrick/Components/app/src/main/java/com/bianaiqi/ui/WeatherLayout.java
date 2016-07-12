package com.bianaiqi.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bianaiqi.components.R;
import com.bianaiqi.util.RandomGenerator;

/**
 * Created by Carrick on 2016/7/12.
 */
public abstract class WeatherLayout extends FrameLayout {

    private final int SUN_DAY = 0;
    private final int SUN_NIGHT = 1;
    private final int CLOUD = 2;
    private final int RAIN = 3;
    private final int THUNDERSTORM = 4;
    private final int SNOW = 5;
    private final int FOG = 6;
    private final int WEATHER_TYPE_COUNT = 7;

    public ImageView mWeatherIcon;
    public boolean mIsPlaying;
    private Context mContext;

    public WeatherLayout(Context context) {
        super(context);
        mContext = context;
    }

    public WeatherLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public WeatherLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void showWeatherIcon(){
        if(null == mWeatherIcon){
            return;
        }

        Drawable drawable = getWeatherIconDrawable(RandomGenerator.getWeatherType());
        if(null != drawable){
            mWeatherIcon.setBackground(drawable);
        }

        LayoutParams para = (LayoutParams) mWeatherIcon.getLayoutParams();
        Resources mRes = mContext.getResources();
        para.leftMargin = (int) mRes.getDimension(R.dimen.weather_icon_margin_left);
        para.topMargin = (int) mRes.getDimension(R.dimen.weather_icon_margin_top);

        mWeatherIcon.setLayoutParams(para);
    }

    private Drawable getWeatherIconDrawable(int type){
        Resources mRes = mContext.getResources();
        Drawable drawable;
        switch (type){
            case SUN_NIGHT:
                drawable = mRes.getDrawable(R.drawable.icon_moon);
                break;

            case CLOUD:
                drawable = mRes.getDrawable(R.drawable.icon_cloud);
                break;

            case RAIN:
                drawable = mRes.getDrawable(R.drawable.icon_rain_light);
                break;

            case THUNDERSTORM:
                drawable = mRes.getDrawable(R.drawable.icon_thunderstorm);
                break;

            case FOG:
                drawable = mRes.getDrawable(R.drawable.icon_fog);
                break;

            case SNOW:
                drawable = mRes.getDrawable(R.drawable.icon_snow_light);
                break;

            case SUN_DAY:
            default:
                drawable = mRes.getDrawable(R.drawable.icon_sun);
                break;
        }
        return drawable;
    }

    public boolean getAnimationState(){
        return mIsPlaying;
    }

    public void setAnimationState(boolean state){
        mIsPlaying = state;
    };

    public abstract void startAnimation();

    public abstract void stopAnimation();
}
