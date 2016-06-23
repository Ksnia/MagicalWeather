package com.example.weather.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.weather.MainActivity;
import com.example.weather.data.local.WeatherCity;
import com.example.weather.data.local.WeatherDataItem;
import com.example.weather.manager.WeatherManager;


public class SendWebRequestThread extends Thread {

	protected String cityName = null;
	protected String cityDomain = null;
	protected Handler mHandler = null;

    @Override
	public void run() {
		// TODO Auto-generated method stub
    	Looper.prepare();
    	WeatherCity city = new WeatherCity(cityName,cityDomain);
		WeatherManager manager = new WeatherManager(WeatherManager.Engine.Yahoo, city);
		WeatherDataItem item = manager.getCurWeather();
		if(item != null){
			String information = item.toString();
			Message msg = mHandler.obtainMessage(MainActivity.QUERY_RESPONSE);
			Bundle bundle = new Bundle();
			bundle.putString("key_info", information);
			msg.setData(bundle);
			mHandler.removeMessages(MainActivity.QUERY_RESPONSE);
			mHandler.sendMessage(msg);
		}


		
		
	}

	public SendWebRequestThread(String name,  String domain,
			Handler handler) {
		super();
		cityName = name;
		cityDomain = domain;
		mHandler = handler;
	}
	
	
	
	

}
