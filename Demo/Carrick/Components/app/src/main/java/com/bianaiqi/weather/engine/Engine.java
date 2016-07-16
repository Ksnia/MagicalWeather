package com.bianaiqi.weather.engine;


import android.content.Context;

import com.bianaiqi.util.MyLog;
import com.bianaiqi.weather.data.local.WeatherCity;
import com.bianaiqi.weather.data.local.WeatherDataItem;
import com.bianaiqi.weather.data.net.DownloadInformation;
import com.bianaiqi.weather.connection.http.InternetRequestByHttpConnection;

import java.util.ArrayList;

/**
 * Created by Carrick on 2016/7/12.
 */
public abstract class Engine {

    protected InternetRequestByHttpConnection connection;
    protected WeatherCity city;

    public Engine() {
        this.city = null;
    }

    public Engine(WeatherCity city) {
        this.city = city;
    }

    protected WeatherDataItem getWeatherDataItem(Context context, WeatherCity city) {
        DownloadInformation info = null;
        WeatherDataItem item = null;
        if (connection == null) {
            connection = new InternetRequestByHttpConnection();
        }
        MyLog.d(this.getClass(), "getWeatherDataItem  connection = " + connection);
        if (city != null) {
            info = connection.sendRequest(processCurUrl(context, city));
            MyLog.d(this.getClass(), "getWeatherDataItem  info = " + info);
            if (info != null) {
                item = processCurInfo(info);
            }
        }
        MyLog.d(this.getClass(), "getWeatherDataItem  item = " + item);
        return item;
    }

    protected ArrayList<WeatherDataItem> getWeatherDataList(Context context, WeatherCity city) {
        DownloadInformation info = null;
        ArrayList<WeatherDataItem> list = null;
        if (connection == null) {
            connection = new InternetRequestByHttpConnection();
        }
        if (city != null) {
            info = connection.sendRequest(processForeastUrl(context, city));
            if (info != null) {
                list = processForesstInfo(info);
            }
        }
        return list;
    }

    public abstract WeatherDataItem getCurWeatherData(Context context);

    public abstract ArrayList<WeatherDataItem> getForeastWeatherData(Context context);

    protected abstract WeatherDataItem processCurInfo(DownloadInformation info);

    protected abstract String processCurUrl(Context context, WeatherCity city);

    protected abstract String processForeastUrl(Context context, WeatherCity city);

    protected abstract ArrayList<WeatherDataItem> processForesstInfo(DownloadInformation info);
}
