package com.bianaiqi.weather;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import com.bianaiqi.util.MyLog;
import com.bianaiqi.weather.data.local.WeatherCity;
import com.bianaiqi.weather.data.local.WeatherDataItem;
import com.bianaiqi.weather.engine.Engine;
import com.bianaiqi.weather.engine.EngineFactory;

/**
 * Created by Carrick on 2016/7/13.
 */
public class WeatherRequestTask extends AsyncTask {
    private Context mContext;
    private Engine mEngine;
    private int mRequestType;
    private WeatherRequestHandler mListener;

    public interface WeatherRequestHandler {
        void requestSuccessful(WeatherDataItem data);

        void requestSuccessful(ArrayList<WeatherDataItem> list);

        void requestFailed();
    }

    public WeatherRequestTask(Context context, EngineFactory.EngineType type, WeatherCity city, int requestType) {
        this.mContext = context;
        this.mRequestType = requestType;
        mEngine = EngineFactory.createEngine(city, type);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            MyLog.d(this.getClass(), "doInBackground  mRequestType = " + mRequestType);
            if (WeatherConstant.QUERY_REQUEST_ITEM == mRequestType) {
                WeatherDataItem data = getCurData();
                MyLog.d(this.getClass(), "doInBackground  data = " + data);
                return data;
            } else if (WeatherConstant.QUERY_REQUEST_LIST == mRequestType) {
                ArrayList<WeatherDataItem> list = getForeastData();
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            // TODO handle different exception cases
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object object) {
        // Done on UI Thread
        MyLog.d(this.getClass(), "onPostExecute mRequestType = " + mRequestType + "; Object = " + object);
        try {
            if (WeatherConstant.QUERY_REQUEST_ITEM == mRequestType) {
                if (null != object) {
                    WeatherDataItem item = (WeatherDataItem) object;
                    onSuccessRequset(item);
                } else {
                    onFailRequset();
                }
            } else if (WeatherConstant.QUERY_REQUEST_LIST == mRequestType) {
                MyLog.d(this.getClass(), "onPostExecute  mRequestType = QUERY_RESPONSE_LIST");
                onFailRequset();
            } else {
                MyLog.d(this.getClass(), "onPostExecute  mRequestType = else");
                onFailRequset();
            }
        } catch (Exception e) {
            MyLog.d(this.getClass(), "onPostExecute  Exception = " + e);
            onFailRequset();
        }
    }

    private WeatherDataItem getCurData() {
        MyLog.d(this.getClass(), "getCurData  ");
        WeatherDataItem item = null;
        if (mEngine != null) {
            item = mEngine.getCurWeatherData(mContext);
        }
        return item;
    }

    private ArrayList<WeatherDataItem> getForeastData() {
        ArrayList<WeatherDataItem> list = null;
        if (mEngine != null) {
            list = mEngine.getForeastWeatherData(mContext);
        }
        return list;
    }

    public void setListener(WeatherRequestHandler listener) {
        mListener = listener;
    }

    private void onSuccessRequset(WeatherDataItem data) {
        MyLog.d(this.getClass(), "onSuccessRequset = " + data.toString());
        if (null == mListener) {
            return;
        }
        mListener.requestSuccessful(data);
    }

    private void onSuccessRequset(ArrayList<WeatherDataItem> list) {
        MyLog.d(this.getClass(), "onSuccessRequset = " + list);
        if (null == mListener) {
            return;
        }
        mListener.requestSuccessful(list);
    }

    private void onFailRequset() {
        MyLog.d(this.getClass(), "onFailRequset = ");
        if (null == mListener) {
            return;
        }
        mListener.requestFailed();
    }
}
