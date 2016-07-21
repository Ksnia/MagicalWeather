package com.bianaiqi.manager;

import android.content.Context;
import android.view.ViewGroup;

import com.bianaiqi.ui.CloudLayout;
import com.bianaiqi.ui.FogLayout;
import com.bianaiqi.ui.RainLayout;
import com.bianaiqi.ui.SnowLayout;
import com.bianaiqi.ui.SunDayLayout;
import com.bianaiqi.ui.SunNightLayout;
import com.bianaiqi.ui.ThunderStormLayout;
import com.bianaiqi.ui.WeatherLayout;
import com.bianaiqi.util.MyLog;
import com.bianaiqi.weather.WeatherConstant;
import com.bianaiqi.weather.WeatherUtils;
import com.bianaiqi.weather.data.local.WeatherDataItem;

/**
 * Created by Carrick on 2016/7/13.
 */
public class WeatherLayoutManager implements WeatherManager.WeatherLayoutUpdateListener {

    private WeatherLayout mWeatherLayout;
    private ViewGroup mParent;
    private Context mContext;
    public int mLayoutType;
    public static WeatherLayoutManager sWLMgr;

    public WeatherLayoutManager() {

    }

    public static WeatherLayoutManager getInstance() {
        if (null == sWLMgr) {
            sWLMgr = new WeatherLayoutManager();
        }
        return sWLMgr;
    }

    public void init(Context context, ViewGroup parent) {
        mContext = context;
        mParent = parent;
        setWeatherLayoutType(WeatherConstant.DEFAULT_WEATHTER_TYPE);
        loadWeatherLayout();
    }

    @Override
    public void onWeatherLayoutUpdate(WeatherDataItem data) {
        MyLog.d(this.getClass(), "data = " + data);
        if (null == data) {
            return;
        }

        int newLayoutType = getLayoutTypeByData(data);
        if (getWeatherLayoutType() == newLayoutType) {
            if (mWeatherLayout.getAnimationState() == true) {
                mWeatherLayout.stopAnimation();
            }
            mWeatherLayout.startAnimation();
            return;
        }
        setWeatherLayoutType(newLayoutType);
        loadWeatherLayout();
    }

    public boolean isNight() {
        boolean night = false;
        return night;
    }

    private void loadWeatherLayout() {
        // 此处parent要替换为系统的某一个view
        if (null != mWeatherLayout) {
            mParent.removeView(mWeatherLayout);
            mWeatherLayout = null;
        }

        if (null != mParent) {
            //mWeatherLayout = getWeatherViewLayout(WeatherUtils.getTestWeatherLayoutType());
            mWeatherLayout = getWeatherViewLayout(getWeatherLayoutType());
            mParent.addView(mWeatherLayout);
        }
    }

    public int getWeatherLayoutType() {
        return mLayoutType;
    }

    private void setWeatherLayoutType(int type) {
        mLayoutType = type;
    }

    private int getLayoutTypeByData(WeatherDataItem data) {
        int type;
        switch (data.getWeatherId()) {
            case Sunny:
                if (isNight()) {
                    type = WeatherConstant.SUN_NIGHT;
                } else {
                    type = WeatherConstant.SUN_DAY;
                }
                break;

            case Cloud:
                type = WeatherConstant.CLOUD;
                break;

            case Foggy:
                type = WeatherConstant.FOG;
                break;

            case LittleRain:
                type = WeatherConstant.RAIN_LIGHT;
                break;

            case HeavyRain:
                type = WeatherConstant.RAIN_HEAVY;
                break;

            case Thundershowers:
                type = WeatherConstant.THUNDERSTORM;
                break;

            case LittleSnow:
            case HeavySnow:
            case MixedRainAndSnow:
                type = WeatherConstant.SNOW_LIGHT;
                break;

            case Others:
            default:
                type = WeatherConstant.DEFAULT_WEATHTER_TYPE;
                break;
        }
        return type;
    }

    private WeatherLayout getWeatherViewLayout(int type) {
        WeatherLayout ly;
        MyLog.d(this.getClass(), "getWeatherViewLayout type = " + type);
        switch (type) {
            case WeatherConstant.SUN_NIGHT:
                ly = new SunNightLayout(mContext);
                break;

            case WeatherConstant.CLOUD:
                ly = new CloudLayout(mContext);
                break;

            case WeatherConstant.RAIN_LIGHT:
            case WeatherConstant.RAIN_HEAVY:
                ly = new RainLayout(mContext);
                break;

            case WeatherConstant.THUNDERSTORM:
                ly = new ThunderStormLayout(mContext);
                break;

            case WeatherConstant.FOG:
                ly = new FogLayout(mContext);
                break;

            case WeatherConstant.SNOW_LIGHT:
            case WeatherConstant.SNOW_HEAVY:
            case WeatherConstant.SLEET:
                ly = new SnowLayout(mContext);
                break;

            case WeatherConstant.SUN_DAY:
            default:
                ly = new SunDayLayout(mContext);
                break;
        }
        return ly;
    }

    public void doAnimation(boolean visibility) {
        if (null == mWeatherLayout) {
            return;
        }

        if (visibility) {
            if (mWeatherLayout.getAnimationState() == true) {
                mWeatherLayout.startAnimation();
            }
            mWeatherLayout.startAnimation();
        } else {
            if (mWeatherLayout.getAnimationState() == true) {
                mWeatherLayout.stopAnimation();
            }
        }
    }
}
