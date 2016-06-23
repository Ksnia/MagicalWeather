package com.example.weather.manager;

import com.example.weather.data.local.WeatherCity;
import com.example.weather.data.local.WeatherDataItem;

import java.util.ArrayList;

public class WeatherManager {

	public enum Engine {
		Yahoo, Baidu
	}

	private Engine engineType;
	private WeatherCity city;
	private EngineManager manager;
	
	public WeatherManager(Engine engineType, WeatherCity city) {
		super();
		this.engineType = engineType;
		this.city = city;
		manager = processEngine(engineType);
	}

	public WeatherDataItem getCurWeather() {
		WeatherDataItem item = null;
		if(manager != null && city != null){
			item = manager.getCurWeather();
		}
		return item;

	}

	public ArrayList<WeatherDataItem> getForeastWeather() {
		ArrayList<WeatherDataItem> list = null;
		if(manager != null && city != null){
			list = manager.getForeastWeather();
		}
		return list;
	}

	private EngineManager processEngine(Engine type){
		switch (type) {
		case Baidu:
			manager = new BaiduEngineManager(city);
			break;
		case Yahoo:
			default:
			manager = new YahooEngineManager(city);
			break;
		}
		return manager;
		
	}
	

	
}
