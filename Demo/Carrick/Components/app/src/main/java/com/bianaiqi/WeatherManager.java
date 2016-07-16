package com.bianaiqi;

import com.bianaiqi.WeatherRequestTask;
import com.bianaiqi.weather.data.local.WeatherCity;
import com.bianaiqi.weather.engine.Engine;
import com.bianaiqi.weather.engine.EngineFactory;


import android.util.Log;
import android.view.View;


/**
 * Created by Carrick on 2016/7/12.
 */

// 实现时钟的接口 时间变化 自动更新实时数据
// 定义接口，由WeatherLayout实现，一旦数据更新，更新Layout
// get数据失败怎么办？ 是否有错误返回码？ 例如 网络请求失败、网络未连接等，便于提示！
public class WeatherManager implements WeatherRequestTask.WeatherRequestHandler {

    public static WeatherManager sManager;
    private Engine mEngine;
    private WeatherUpdateListner mListener;
    //private WeatherRequestTask mTask;
    private View view; // clockView

    public interface WeatherUpdateListner {
        void onDataChanged(int type);
    }

    public WeatherManager() {
        if (null == mEngine) {
            mEngine = EngineFactory.createEngine(EngineFactory.DEFAULT_ENGINE_TYPE);
        }
    }

    public static synchronized WeatherManager getInstance() {
        if (null == sManager) {
            sManager = new WeatherManager();
        }
        return sManager;
    }

    public void setQueryCity(String cityName, String cityDomain) {
        WeatherCity city = new WeatherCity(cityName, cityDomain);
        if (null != mEngine) {
            mEngine.setQueryCity(city);
        }
    }

    public void requestWeatherData() {
        /*
        if(null != mTask ){
            if(!mTask.isCancelled()){
                mTask.cancel(true);
            }
            mTask = null;
        }
        */
        WeatherRequestTask mTask = new WeatherRequestTask();
        mTask.setEngine(mEngine);
        setQueryCity("", "");
        mTask.setRequestType(WeatherRequestTask.DEFAULT_QUERY_REQUEST);
        mTask.addListener(sManager);
        //mTask.addListener(view); // clockView
        mTask.execute();
    }

    private void updateWeather(int type) {
        if (!isDataChanged()) {
            return;
        }

        if (null != mListener) {
            mListener.onDataChanged(type);
        }
    }

    private boolean isDataChanged() {
        boolean change = true;

        return change;
    }

    public void addListener(WeatherUpdateListner listner) {
        mListener = listner;
    }

    public void removeListener() {
        mListener = null;
    }

    @Override
    public void requestSuccessful(String string) {
        int i = 0;
        Log.d("Carrick","    string = " + string);
        updateWeather(i);

        /*
        if(null != mTask && !mTask.isCancelled()){
            mTask.cancel(true);
            mTask = null;
        }
        */
    }

    @Override
    public void requestFailed() {
        /*
        if(null != mTask && !mTask.isCancelled()){
            mTask.cancel(true);
            mTask = null;
        }
        */
    }
}
