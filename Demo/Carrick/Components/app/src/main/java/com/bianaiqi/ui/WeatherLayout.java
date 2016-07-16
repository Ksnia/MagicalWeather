package com.bianaiqi.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bianaiqi.WeatherLayoutManager;
import com.bianaiqi.components.R;
import com.bianaiqi.weather.WeatherConstant;

/**
 * Created by Carrick on 2016/7/12.
 */
public abstract class WeatherLayout extends FrameLayout {

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

    public void showWeatherIcon() {
        if (null == mWeatherIcon) {
            return;
        }

        Drawable drawable = getWeatherIconDrawable(WeatherLayoutManager.getInstance().getWeatherLayoutType());
        if (null != drawable) {
            mWeatherIcon.setBackground(drawable);
        }

        LayoutParams para = (LayoutParams) mWeatherIcon.getLayoutParams();
        Resources mRes = mContext.getResources();
        para.leftMargin = (int) mRes.getDimension(R.dimen.weather_icon_margin_left);
        para.topMargin = (int) mRes.getDimension(R.dimen.weather_icon_margin_top);

        mWeatherIcon.setLayoutParams(para);
    }

    private Drawable getWeatherIconDrawable(int type) {
        Resources mRes = mContext.getResources();
        Drawable drawable;
        switch (type) {
            case WeatherConstant.SUN_NIGHT:
                drawable = mRes.getDrawable(R.drawable.icon_moon);
                break;

            case WeatherConstant.CLOUD:
                drawable = mRes.getDrawable(R.drawable.icon_cloud);
                break;

            case WeatherConstant.RAIN_LIGHT:
                drawable = mRes.getDrawable(R.drawable.icon_rain_light);
                break;

            case WeatherConstant.RAIN_HEAVY:
                drawable = mRes.getDrawable(R.drawable.icon_rain_heavy);
                break;

            case WeatherConstant.THUNDERSTORM:
                drawable = mRes.getDrawable(R.drawable.icon_thunderstorm);
                break;

            case WeatherConstant.FOG:
                drawable = mRes.getDrawable(R.drawable.icon_fog);
                break;

            case WeatherConstant.SNOW_LIGHT:
                drawable = mRes.getDrawable(R.drawable.icon_snow_light);
                break;

            case WeatherConstant.SNOW_HEAVY:
                drawable = mRes.getDrawable(R.drawable.icon_snow_heavy);
                break;

            case WeatherConstant.SLEET:
                drawable = mRes.getDrawable(R.drawable.icon_sleet);
                break;

            case WeatherConstant.SUN_DAY:
            default:
                drawable = mRes.getDrawable(R.drawable.icon_sun);
                break;
        }
        return drawable;
    }

    public boolean getAnimationState() {
        return mIsPlaying;
    }

    public void setAnimationState(boolean state) {
        mIsPlaying = state;
    }

    public abstract void startAnimation();

    public abstract void stopAnimation();
}
