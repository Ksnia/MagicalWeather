package com.bianaiqi;

import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.bianaiqi.util.MyLog;
import com.bianaiqi.weather.data.local.WeatherDataItem;
import com.bianaiqi.weather.engine.Engine;

/**
 * Created by Carrick on 2016/7/13.
 */
public class WeatherRequestTask extends AsyncTask {

    public static final int QUERY_REQUEST_ITEM = 1;
    public static final int QUERY_REQUEST_LIST = 2;
    public static final int QUERY_REQUEST_NONE = 3;
    public static final int DEFAULT_QUERY_REQUEST = QUERY_REQUEST_ITEM;

    private int mRequestType;
    private Engine mEngine;
    private final Set<WeatherRequestHandler> mListeners = Collections.newSetFromMap(
            new ConcurrentHashMap<WeatherRequestHandler, Boolean>(8, 0.9f, 1));

    public interface WeatherRequestHandler {
        void requestSuccessful(String string);

        void requestFailed();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            MyLog.d(this.getClass(), "doInBackground  mRequestType = " + mRequestType);
            if (QUERY_REQUEST_ITEM == mRequestType) {
                WeatherDataItem data = getCurData();
                MyLog.d(this.getClass(), "    doInBackground  data = " + data);
                return data;
            } else if (QUERY_REQUEST_LIST == mRequestType) {
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
        MyLog.d(this.getClass(), " onPostExecute mRequestType = " + mRequestType + "; Object = " + object);
        try {
            if (QUERY_REQUEST_ITEM == mRequestType) {
                if (null != object) {
                    WeatherDataItem item = (WeatherDataItem) object;
                    String str = "";
                    if (item != null) {
                        str = item.toString();
                    }
                    MyLog.d(this.getClass(), "    str = " + str);
                    onSuccessRequset(str);
                } else {
                    onFailRequset();
                }
            } else if (QUERY_REQUEST_LIST == mRequestType) {
                MyLog.d(this.getClass(), "    onPostExecute  mRequestType = QUERY_RESPONSE_LIST");
                onFailRequset();
            } else {
                MyLog.d(this.getClass(), "    onPostExecute  mRequestType = else");
                onFailRequset();
            }
        } catch (Exception e) {
            MyLog.d(this.getClass(), "    onPostExecute  Exception = " + e);
            onFailRequset();
        }
    }

    public void setRequestType(int type) {
        mRequestType = type;
    }

    public void setEngine(Engine engine) {
        mEngine = engine;
    }

    public void addListener(WeatherRequestHandler listener) {
        mListeners.add(listener);
    }

    private WeatherDataItem getCurData() {
        MyLog.d(this.getClass(), "getCurData  ");
        WeatherDataItem item = null;
        if (mEngine != null) {
            item = mEngine.getCurWeatherData();
        }
        return item;
    }

    private ArrayList<WeatherDataItem> getForeastData() {
        ArrayList<WeatherDataItem> list = null;
        if (mEngine != null) {
            list = mEngine.getForeastWeatherData();
        }
        return list;
    }

    private void onSuccessRequset(String str) {
        MyLog.d(this.getClass(), "    onSuccessRequset = " + str);
        if (null == mListeners) {
            return;
        }

        for (WeatherRequestHandler listener : mListeners) {
            MyLog.d(this.getClass(), "    mListeners requestSuccessful ");
            listener.requestSuccessful(str);
        }
    }

    private void onFailRequset() {
        MyLog.d(this.getClass(), "    onFailRequset = ");
        if (null == mListeners) {
            return;
        }

        for (WeatherRequestHandler listener : mListeners) {
            MyLog.d(this.getClass(), "    mListeners requestFailed ");
            listener.requestFailed();
        }
    }
}
