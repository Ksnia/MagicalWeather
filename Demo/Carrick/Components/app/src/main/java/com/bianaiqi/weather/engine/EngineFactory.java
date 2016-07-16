package com.bianaiqi.weather.engine;

import com.bianaiqi.weather.data.local.WeatherCity;

/**
 * Created by Carrick on 2016/7/12.
 */
public class EngineFactory {

    public static enum EngineType {
        YAHOO, BAIDU
    }

    public static final EngineType DEFAULT_ENGINE_TYPE = EngineType.YAHOO;

    public static Engine createEngine(EngineType type) {
        Engine engine;
        switch (type) {
            case BAIDU:
                engine = new BaiduEngine();
                break;

            case YAHOO:
            default:
                engine = new YahooEngine();
                break;
        }
        return engine;
    }

    public static Engine createEngine(WeatherCity city, EngineType type) {
        Engine engine;
        switch (type) {
            case BAIDU:
                engine = new BaiduEngine(city);
                break;

            case YAHOO:
            default:
                engine = new YahooEngine(city);
                break;
        }
        return engine;
    }
}