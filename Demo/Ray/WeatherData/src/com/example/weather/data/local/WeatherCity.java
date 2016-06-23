package com.example.weather.data.local;

public class WeatherCity {
	long latitude;
	long longitude;
	String name;
	String country;

	public WeatherCity(String name, String country) {
		this.name = name;
		this.country = country;
	}

	public WeatherCity(long latitude, long longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	public long getLatitude() {
		return latitude;
	}
	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}
	public long getLongitude() {
		return longitude;
	}
	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
}
