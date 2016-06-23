package com.example.weather.data.net.bean.baidu;

import java.util.List;

public class BaiduOriginResult {

	String currentCity;
	int pm25;
	List <BaiduWeatherOtherItem>index;
	List <BaiduWeatherDataItem>weather_data;
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public int getPm25() {
		return pm25;
	}
	public void setPm25(int pm25) {
		this.pm25 = pm25;
	}
	public List<BaiduWeatherOtherItem> getIndex() {
		return index;
	}
	public void setIndex(List<BaiduWeatherOtherItem> index) {
		this.index = index;
	}
	public List<BaiduWeatherDataItem> getWeather_data() {
		return weather_data;
	}
	public void setWeather_data(List<BaiduWeatherDataItem> weather_data) {
		this.weather_data = weather_data;
	}
	
	
}
