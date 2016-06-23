package com.example.weather.manager;

import com.example.weather.data.net.DownloadInformation;
import com.example.weather.data.net.bean.baidu.BaiduOriginData;
import com.example.weather.data.local.WeatherCity;
import com.example.weather.data.local.WeatherDataItem;
import com.example.weather.data.net.bean.baidu.BaiduWeatherDataItem;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/22.
 */
public class BaiduEngineManager extends  EngineManager {
   // 只支持中國城市天氣
    private static final String URL_BAIDU_CITY_WEATHER_QUERY =
            "http://api.map.baidu.com/telematics/v3/weather?location='%s'&output=json&ak=6tYzTvGZSOpYB5Oc2YGGOKt8";

    private static String Sunny = "晴";
    private static String Cloud = "多雲";
    private static String Foggy = "霧";
    private static String LittleRain = "小雨";
    private static String HeavyRain = "大雨";
    private static String Thundershowers = "雷陣雨";
    private static String LittleSnow = "小雪";
    private static String HeavySnow = "大雪";
    private static String MixedRainAndSnow = "雨夾雪";




    public BaiduEngineManager(WeatherCity city) {
        super(city);
    }

    @Override
    public WeatherDataItem getCurWeather() {
        return getWeatherDataItem(city);
    }

    @Override
    public ArrayList<WeatherDataItem> getForeastWeather() {
        return getWeatherDataList(city);
    }

    @Override
    protected String processCurUrl(WeatherCity city) {
        String finalUrl = String.format(URL_BAIDU_CITY_WEATHER_QUERY,city.getName());
        return finalUrl;
    }

    @Override
    protected WeatherDataItem processCurInfo(DownloadInformation info) {
        WeatherDataItem item = new WeatherDataItem();
        BaiduOriginData ret = com.example.weather.util.AppUtil.getGson().fromJson(info.toString(), BaiduOriginData.class);
        if(ret != null && ret.getResults()!= null && ret.getResults().size()>0){
           BaiduWeatherDataItem weather_data =  ret.getResults().get(0).getWeather_data().get(0);
            item.setWeather(weather_data.getWeather());
            item.setCurTemp(Integer.valueOf(weather_data.getTemperature()));
            item.setHightTemp(0);//24 ~ 19℃
            item.setLowTemp(0);
            item.setCity(city);
            item.setDate(new Date(weather_data.getDate())); //周日 05月22日 (实时：21℃)
            item.setWeatherId(getFinalCode(weather_data.getWeather()));
        }
        return item;
    }

    @Override
    protected ArrayList<WeatherDataItem> processForesstInfo(DownloadInformation info) {
        return null;
    }

    @Override
    protected String processForeastUrl(WeatherCity city) {
        return processCurUrl(city);
    }


    private int getCurTemp(String t){
        int i = 0;

        return i;

    }

    private int getLowTemp (String t ){
        int i = 0;

        return i;
    }

   private int getHighTemp (String t) {
       int i = 0;

       return i;
   }


    private WeatherDataItem.WeatherCode getFinalCode(String weather){
        WeatherDataItem.WeatherCode code = WeatherDataItem.WeatherCode.Others;
        if(weather.contains(Sunny)){
            code = WeatherDataItem.WeatherCode.Sunny;
        }else if(weather.contains(Cloud)){
            code = WeatherDataItem.WeatherCode.Cloud;
        }else if(weather.contains(Foggy)){
            code = WeatherDataItem.WeatherCode.Foggy;
        }else if(weather.contains(LittleRain)){
            code = WeatherDataItem.WeatherCode.LittleRain;
        }else if(weather.contains(HeavyRain)){
            code = WeatherDataItem.WeatherCode.HeavyRain;
        }else if(weather.contains(Thundershowers)){
            code = WeatherDataItem.WeatherCode.Thundershowers;
        }else if(weather.contains(LittleSnow)){
            code = WeatherDataItem.WeatherCode.LittleSnow;
        }else if(weather.contains(HeavySnow)){
            code = WeatherDataItem.WeatherCode.HeavySnow;
        }else if(weather.contains(MixedRainAndSnow)){
            code = WeatherDataItem.WeatherCode.MixedRainAndSnow;
        }

        return  code;
    }






}
