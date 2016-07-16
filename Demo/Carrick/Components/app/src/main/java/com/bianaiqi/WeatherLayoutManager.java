package com.bianaiqi;

import android.content.Context;
import android.view.ViewGroup;

import com.bianaiqi.WeatherManager;
import com.bianaiqi.ui.CloudLayout;
import com.bianaiqi.ui.FogLayout;
import com.bianaiqi.ui.RainLayout;
import com.bianaiqi.ui.SnowLayout;
import com.bianaiqi.ui.SunDayLayout;
import com.bianaiqi.ui.SunNightLayout;
import com.bianaiqi.ui.ThunderStormLayout;
import com.bianaiqi.ui.WeatherLayout;
import com.bianaiqi.weather.WeatherConstant;

/**
 * Created by Carrick on 2016/7/13.
 */
public class WeatherLayoutManager implements WeatherManager.WeatherUpdateListner {

    private WeatherLayout mWeatherLayout;
    private ViewGroup mParent;
    private Context mContext;
    public int mType;
    public static WeatherLayoutManager sWLMgr;

    public WeatherLayoutManager() {

    }

    public static WeatherLayoutManager getInstance(){
        if(null == sWLMgr){
            sWLMgr = new WeatherLayoutManager();
        }
        return sWLMgr;
    }

    public void init(Context context, ViewGroup parent){
        mContext = context;
        mParent = parent;
    }

    @Override
    public void onDataChanged(int type) {
        setWeatherType(type);

        // 此处parent要替换为系统的某一个view
        if(null != mWeatherLayout){
            mParent.removeView(mWeatherLayout);
            mWeatherLayout = null;
        }

        if(null != mParent){
            mWeatherLayout = getWeatherViewLayout(type);
            mParent.addView(mWeatherLayout);
        }
    }

    private WeatherLayout getWeatherViewLayout(int type){
        WeatherLayout ly;
        switch (type){
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

    private void setWeatherType(int type){
        mType = type;
    }

    public int getWeatherType(){
        return mType;
    }
}
