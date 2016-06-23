package com.huaqin.android.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Weather implements Parcelable {

	private String mDay;
	private String mDate;
	private int mLowTemp;
	private int mHighTemp;
	private String mText;
	private int mCode;
	private String mTempFormat;
	private String mSunrise;
	private String mSunset;
	private String mHumidity;
	private String mPressure;
	private String mPubDate;

	public Weather() {

	}

	public String getTempFormat() {
		return mTempFormat;
	}

	public void setTempFormat(String strTempFormat) {
		this.mTempFormat = strTempFormat;
	}

	public String getDay() {
		return mDay;
	}

	public void setDay(String strDay) {
		this.mDay = strDay;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String strDate) {
		this.mDate = strDate;
	}

	public int getLowTemp() {
		return mLowTemp;
	}

	public void setLowTemp(int lowTemp) {
		this.mLowTemp = lowTemp;
	}

	public int getHighTemp() {
		return mHighTemp;
	}

	public void setHighTemp(int highTemp) {
		this.mHighTemp = highTemp;
	}

	public String getText() {
		return mText;
	}

	public void setText(String strText) {
		this.mText = strText;
	}

	public int getCode() {
		return mCode;
	}

	public void setCode(int code) {
		this.mCode = code;
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

	public String getPubDate() {
		return mPubDate;
	}

	public void setPubDate(String pubdate) {
		this.mPubDate = pubdate;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flag) {
		parcel.writeString(mDay);
		parcel.writeString(mDate);
		parcel.writeInt(mLowTemp);
		parcel.writeInt(mHighTemp);
		parcel.writeString(mText);
		parcel.writeInt(mCode);
		parcel.writeString(mTempFormat);
		parcel.writeString(mSunrise);
		parcel.writeString(mSunset);
		parcel.writeString(mHumidity);
		parcel.writeString(mPressure);
		parcel.writeString(mPubDate);
	}

	public static final Creator<Weather> CREATOR = new Creator<Weather>() {
		public Weather createFromParcel(Parcel in) {
			return new Weather(in);
		}

		public Weather[] newArray(int size) {
			return new Weather[size];
		}
	};

	private Weather(Parcel in) {
		mDay = in.readString();
		mDate = in.readString();
		mLowTemp = in.readInt();
		mHighTemp = in.readInt();
		mText = in.readString();
		mCode = in.readInt();
		mTempFormat = in.readString();
		mSunrise = in.readString();
		mSunset = in.readString();
		mHumidity = in.readString();
		mPressure = in.readString();
		mPubDate = in.readString();
	}
}
