package com.bianaiqi.weather.engine;


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

    public void setQueryCity(WeatherCity city){
        this.city = city;
    }

    protected  WeatherDataItem getWeatherDataItem(WeatherCity city){
        DownloadInformation  info = null ;
        WeatherDataItem item = null ;
        if(connection == null){
            connection = new InternetRequestByHttpConnection();
        }
        MyLog.d(this.getClass(), "getCurWeatherData  connection = " + connection);
        if( city != null){
            info = connection.sendRequest(processCurUrl(city));
            MyLog.d(this.getClass(), "getCurWeatherData  info = " + info);
            if( info != null){
                item = processCurInfo(info);
            }
        }
        MyLog.d(this.getClass(), "getCurWeatherData  item = " + item);
        return item;
    }

    protected  ArrayList<WeatherDataItem>  getWeatherDataList(WeatherCity city){
        DownloadInformation  info = null ;
        ArrayList<WeatherDataItem> list = null ;
        if(connection == null){
            connection = new InternetRequestByHttpConnection();
        }
        if( city != null){
            info = connection.sendRequest(processForeastUrl(city));
            if( info != null){
                list = processForesstInfo(info);
            }
        }
        return list;
    }

    public abstract WeatherDataItem getCurWeatherData ();
    public abstract ArrayList<WeatherDataItem> getForeastWeatherData();

    protected abstract  WeatherDataItem processCurInfo(DownloadInformation info);
    protected abstract  String processCurUrl(WeatherCity city);

    protected abstract  String processForeastUrl(WeatherCity city);
    protected abstract  ArrayList<WeatherDataItem> processForesstInfo(DownloadInformation info);
}
