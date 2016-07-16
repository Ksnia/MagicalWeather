package com.bianaiqi.weather.engine;

import android.location.Location;

import com.bianaiqi.util.MyLog;
import com.bianaiqi.weather.WeatherUtils;
import com.bianaiqi.weather.data.local.WeatherCity;
import com.bianaiqi.weather.data.local.WeatherDataItem;
import com.bianaiqi.weather.data.net.DownloadInformation;
import com.bianaiqi.weather.data.net.bean.yahoo.YahooOriginaData;
import com.bianaiqi.weather.data.net.bean.yahoo.YahooQueryResult;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Carrick on 2016/7/12.
 */
public class YahooEngine  extends Engine {

    private static final String URL_YAHOO_CITY_WEATHER_QUERY = "https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where u= '%s' and woeid in (select woeid from geo.places(1) where text='%s')&format=json";

    public YahooEngine() {
        super();
    }

    @Override
    public WeatherDataItem getCurWeatherData() {
        MyLog.d(this.getClass(), "Yahoo getCurWeatherData  city = " + city);
        return getWeatherDataItem(city);
    }

    @Override
    public ArrayList<WeatherDataItem> getForeastWeatherData() {
        return getWeatherDataList(city);
    }

    @Override
    protected String processCurUrl(WeatherCity city) {
        String finalUrl = null ;
        String loc = null;
        if(city.getName() == null || "".equals(city.getName().trim())){//可以从名称 或者  径度 纬度来判断
            Location l = WeatherUtils.getCurLocation();
            MyLog.d(this.getClass(), "processCurUrl +++  l = " + l);
            if(l != null){
                loc = String.format("(%f,%f)", l.getLatitude(),l.getLongitude());
            }
            MyLog.d(this.getClass(), "processCurUrl +++  loc = " + loc);
        }else{
            loc = String.format("%s,%s", city.getName(),city.getCountry());
            MyLog.d(this.getClass(), "processCurUrl ---  loc = " + loc);
        }
        finalUrl = String.format(URL_YAHOO_CITY_WEATHER_QUERY,"c", loc).trim().replaceAll(" ", "%20");
        MyLog.d(this.getClass(), "processCurUrl  finalUrl = " + finalUrl);
        return finalUrl;
    }

    @Override
    protected WeatherDataItem processCurInfo(DownloadInformation info) {
        WeatherDataItem item = new WeatherDataItem() ;
        item.setCity(city);
        YahooOriginaData ret = com.bianaiqi.weather.WeatherUtils.getGson().fromJson(info.toString(), YahooOriginaData.class);
        MyLog.d(this.getClass(), "Yahoo processCurInfo  ret = " + ret);
        if (ret != null && ret.getQuery() != null && ret.getQuery().getCount() > 0){
            YahooQueryResult.Condition  con  = ret.getQuery().getResults().getChannel().getItem().getCondition();
            item.setCurTemp(con.getTemp());
            item.setWeatherId(getFinalCode(con.getCode()));
            item.setWeather(con.getText());
            item.setDate(new Date(con.getDate()));
            item.setHightTemp(0);
            item.setLowTemp(0);
            item.setTempType(1);
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

    /****
     find it from  https://developer.yahoo.com/weather/documentation.html
     0	tornado 龙卷风
     1	tropical storm
     2	hurricane
     3	severe thunderstorms
     4	thunderstorms
     //5	mixed rain and snow
     //6	mixed rain and sleet
     //7	mixed snow and sleet
     //8	freezing drizzle
     //9	drizzle 蒙蒙细雨
     //10	freezing rain
     //11	showers
     //12	showers
     //13	snow flurries
     //14	light snow showers
     //15	blowing snow
     //16	snow
     17	hail 冰雹
     18	sleet 冰雹 雨夹雪
     19	dust  沙城暴
     // 20	foggy
     // 21	haze
     //22	smoky
     23	blustery 大风
     24	windy 风
     25	cold
     // 26	cloudy
     // 27	mostly cloudy (night)
     // 28	mostly cloudy (day)
     // 29	partly cloudy (night)
     // 30	partly cloudy (day)
     // 31	clear (night)
     // 32	sunny
     //33	fair (night)
     //34	fair (day)
     //35	mixed rain and hail
     36	hot
     37	isolated thunderstorms
     38	scattered thunderstorms
     39	scattered thunderstorms
     40	scattered showers
     //41	heavy snow
     42	scattered snow showers
     //43	heavy snow
     //44	partly cloudy
     //45	thundershowers
     //46	snow showers
     47	isolated thundershowers
     3200	not available
     ***/
    private WeatherDataItem.WeatherCode getFinalCode(int id){
        WeatherDataItem.WeatherCode code = WeatherDataItem.WeatherCode.Others;
        switch (id){
            case 31:
            case 32:
            case 33:
            case 34:
                code = WeatherDataItem.WeatherCode.Sunny;
                break;
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 44:
                code = WeatherDataItem.WeatherCode.Cloud;
                break;
            case 20:
            case 21:
            case 22:
                code = WeatherDataItem.WeatherCode.Foggy;
                break;
            case 11:
            case 12:
            case 8:
            case 9:
            case 10:
                code = WeatherDataItem.WeatherCode.LittleRain;
                break;
            case 35:
            case 6:
                code = WeatherDataItem.WeatherCode.HeavyRain;
                break;
            case 45:
                code = WeatherDataItem.WeatherCode.Thundershowers;
                break;
            case 46:
            case 16:
            case 13:
                code = WeatherDataItem.WeatherCode.LittleSnow;
                break;
            case 41:
            case 43:
            case 15:
                code = WeatherDataItem.WeatherCode.HeavySnow;
                break;
            case 5:
            case 14:
            case 7:
                code = WeatherDataItem.WeatherCode.MixedRainAndSnow;
                break;
            default:
                break;

        }
        return code;
    }
}
