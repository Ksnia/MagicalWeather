package com.huaqin.android.weather.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class CityWeather implements Parcelable {

	private String mWoeid;
	private String mPubDate;
	private String mImgUrl;
	private String mCityName;
	private String mSunrise;
	private String mSunset;
	private String mHumidity;
	private String mPressure;
	private List<Weather> mWeather = new ArrayList<Weather>();

	public CityWeather() {

	}

	public String getWoeid() {
		return mWoeid;
	}

	public void setWoeid(String strWoeid) {
		this.mWoeid = strWoeid;
	}

	public String getCityName() {
		return mCityName;
	}

	public void setCityName(String strCityName) {
		this.mCityName = strCityName;
	}

	public String getPubDate() {
		return mPubDate;
	}

	public void setPubDate(String strPubDate) {
		this.mPubDate = strPubDate;
	}

	public String getImgUrl() {
		return mImgUrl;
	}

	public void setImgUrl(String strImgUrl) {
		this.mImgUrl = strImgUrl;
	}

	public List<Weather> getWeather() {
		return mWeather;
	}

	public void setWeather(Weather weather) {
		mWeather.add(weather);
	}

	public String getSunrise() {
		return mSunrise;
	}

	public void setSunrise(String sunrise) {
		this.mSunrise = sunrise;
	}

	public String getSunset() {
		return mSunset;
	}

	public void setSunset(String sunset) {
		this.mSunset = sunset;
	}

	public String getHumidity() {
		return mHumidity;
	}

	public void setHumidity(String humidity) {
		this.mHumidity = humidity;
	}

	public String getPressure() {
		return mPressure;
	}

	public void setPressure(String pressure) {
		this.mPressure = pressure;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flag) {
		parcel.writeString(mWoeid);
		parcel.writeString(mCityName);
		parcel.writeString(mPubDate);
		parcel.writeString(mImgUrl);
		parcel.writeString(mSunrise);
		parcel.writeString(mSunset);
		parcel.writeString(mHumidity);
		parcel.writeString(mPressure);
		parcel.writeList(mWeather);
	}

	public static final Creator<CityWeather> CREATOR = new Creator<CityWeather>() {
		public CityWeather createFromParcel(Parcel in) {
			return new CityWeather(in);
		}

		public CityWeather[] newArray(int size) {
			return new CityWeather[size];
		}
	};

	@SuppressWarnings("unchecked")
	private CityWeather(Parcel in) {
		mWoeid = in.readString();
		mCityName = in.readString();
		mPubDate = in.readString();
		mImgUrl = in.readString();
		mSunrise = in.readString();
		mSunset = in.readString();
		mHumidity = in.readString();
		mPressure = in.readString();
		mWeather = in.readArrayList(Weather.class.getClassLoader());
	}
}
