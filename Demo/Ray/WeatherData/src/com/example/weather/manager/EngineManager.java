package com.example.weather.manager;

import com.example.weather.data.local.WeatherCity;
import com.example.weather.data.local.WeatherDataItem;
import com.example.weather.data.net.DownloadInformation;
import com.example.weather.connection.http.InternetRequestByHttpConnection;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/22.
 */
public abstract  class EngineManager {

    protected InternetRequestByHttpConnection connection;
    protected WeatherCity city;

    public abstract WeatherDataItem getCurWeather ();
    public abstract ArrayList<WeatherDataItem> getForeastWeather();


    protected abstract  String processCurUrl(WeatherCity city);
    protected abstract  WeatherDataItem processCurInfo(DownloadInformation info);

    protected abstract  String processForeastUrl(WeatherCity city);
    protected abstract  ArrayList<WeatherDataItem> processForesstInfo(DownloadInformation info);

    public EngineManager(WeatherCity city) {
        this.city = city;
    }

    protected  WeatherDataItem getWeatherDataItem(WeatherCity city){
        DownloadInformation  info = null ;
        WeatherDataItem item = null ;
        if(connection == null){
            connection = new InternetRequestByHttpConnection();
        }
        if( city != null){
            info = connection.sendRequest(processCurUrl(city));
            if( info != null){
                item = processCurInfo(info);
            }
        }
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

}
