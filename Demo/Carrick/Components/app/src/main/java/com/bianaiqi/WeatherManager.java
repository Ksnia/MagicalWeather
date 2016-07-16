package com.bianaiqi;

import com.bianaiqi.util.MyLog;
import com.bianaiqi.weather.data.local.WeatherCity;
import com.bianaiqi.weather.data.local.WeatherDataItem;
import com.bianaiqi.weather.engine.EngineFactory;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Carrick on 2016/7/12.
 */

// 实现时钟的接口 时间变化 自动更新实时数据
// get数据失败怎么办？ 是否有错误返回码？ 例如 网络请求失败、网络未连接等，便于提示！
public class WeatherManager implements WeatherRequestTask.WeatherRequestHandler {

    public static WeatherManager sManager;
	private WeatherDataUpdateListener mDataUpdateListener;
    private WeatherLayoutUpdateListener mLayoutUpdateListener;
    private WeatherDataItem mData = null;

    public interface WeatherDataUpdateListener {
        void onWeatherDataUpdate(WeatherDataItem data);
    }

    public interface WeatherLayoutUpdateListener {
        void onWeatherLayoutUpdate(WeatherDataItem data);
    }

    public WeatherManager() {

    }

    public static synchronized WeatherManager getInstance() {
        if (null == sManager) {
            sManager = new WeatherManager();
        }
        return sManager;
    }

    public void setDataUpdateListener(WeatherDataUpdateListener listener) {
        mDataUpdateListener = listener;
    }

    public void setLayoutUpdateListener(WeatherLayoutUpdateListener listener) {
        mLayoutUpdateListener = listener;
    }

    public void requestWeatherData(Context context, EngineFactory.EngineType engineType, WeatherCity city, int requestType) {
        WeatherRequestTask task = new WeatherRequestTask(context, engineType, city, requestType);
        task.setListener(sManager);
        task.execute();
    }

    @Override
    public void requestSuccessful(WeatherDataItem data) {
        MyLog.d(this.getClass(), "string = " + data.toString());
        // preData equal newData ,return
        if (compareWeatherDataItem(data)) {
            return;
        }

        onWeatherDataUpdate(data);
        if (null == data) {
            //refresh tips :  update no data
            return;
        }

        saveCurrentWeatherData(data);
        onWeatherLayoutUpdate(data);
    }

    @Override
    public void requestSuccessful(ArrayList<WeatherDataItem> list) {

    }

    @Override
    public void requestFailed() {
        onWeatherDataUpdate(null);
    }

    private boolean compareWeatherDataItem(WeatherDataItem newData) {
        boolean equal = true;

        if (null == mData) {
            if (null == newData) {
                return equal;
            } else {
                equal = false;
            }
        } else {
            equal = mData.equals(newData);
            MyLog.d(this.getClass(), "equal = " + equal);
        }
        return equal;
    }

    private void saveCurrentWeatherData(WeatherDataItem newData) {
        if (null == newData) {
            return;
        }

        if (null == mData) {
            mData = new WeatherDataItem();
        }
        mData.setCity(newData.getCity());
        mData.setDate(newData.getDate());
        mData.setWeatherId(newData.getWeatherId());
        mData.setTempType(newData.getTempType());
        mData.setCurTemp(newData.getCurTemp());
        mData.setHightTemp(newData.getHightTemp());
        mData.setLowTemp(newData.getLowTemp());
    }

    private void onWeatherDataUpdate(WeatherDataItem data) {
        if (null != mDataUpdateListener) {
            mDataUpdateListener.onWeatherDataUpdate(data);
        }
    }

    private void onWeatherLayoutUpdate(WeatherDataItem data) {
        if (null != mLayoutUpdateListener) {
            mLayoutUpdateListener.onWeatherLayoutUpdate(data);
        }
    }
}
