package com.bianaiqi.weather.data.local;

import java.util.Date;

public class WeatherDataItem {

    public enum WeatherCode {
        Others,
        Sunny,
        Cloud,
        Foggy,
        LittleRain,
        HeavyRain,
        Thundershowers,
        LittleSnow,
        HeavySnow,
        MixedRainAndSnow
    }

    private String weather;
    private WeatherCode WeatherId;
    private int curTemp;
    private int lowTemp;
    private int hightTemp;
    private Date date;
    private WeatherCity city;
    private int tempType;  // 1 : 摄氏度  0：华氏度

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public WeatherCode getWeatherId() {
        return WeatherId;
    }

    public void setWeatherId(WeatherCode WeatherId) {
        this.WeatherId = WeatherId;
    }

    public int getCurTemp() {
        return curTemp;
    }

    public void setCurTemp(int curTemp) {
        this.curTemp = curTemp;
    }

    public int getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(int lowTemp) {
        this.lowTemp = lowTemp;
    }

    public int getHightTemp() {
        return hightTemp;
    }

    public void setHightTemp(int hightTemp) {
        this.hightTemp = hightTemp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public WeatherCity getCity() {
        return city;
    }

    public void setCity(WeatherCity city) {
        this.city = city;
    }

    public int getTempType() {
        return tempType;
    }

    public void setTempType(int tempType) {
        this.tempType = tempType;
    }

    @Override
    public String toString() {
        return "WeatherDataItem{" +
                "weather='" + weather + '\'' +
                ", WeatherId=" + WeatherId +
                ", curTemp=" + curTemp +
                ", lowTemp=" + lowTemp +
                ", hightTemp=" + hightTemp +
                ", date=" + date +
                ", city=" + city +
                ", tempType=" + tempType +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else {
            if (this.getClass() == obj.getClass()) {
                WeatherDataItem o = (WeatherDataItem) obj;
                if (this.getCity().equals(o.getCity()) && this.getDate().equals(o.getDate())
                        && this.getWeatherId().equals(o.getWeatherId())
                        && this.getTempType() == o.getTempType()
                        && this.getCurTemp() == o.getCurTemp()
                        && this.getHightTemp() == o.getHightTemp()
                        && this.getLowTemp() == o.getLowTemp()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }
}
