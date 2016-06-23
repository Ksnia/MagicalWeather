package com.huaqin.android.weather.model;

public class City {

    private String mWoeid;
    private String mName;
    private String mCountry;
    private String mMunicipality;
    private String mPrefecture;
    private String mCounty;

    public String getName() {
        return mName;
    }

    public void setName(String strName) {
        this.mName = strName;
    }

    public String getWoeid() {
        return mWoeid;
    }

    public void setWoeid(String strWoeid) {
        this.mWoeid = strWoeid;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String strCountry) {
        this.mCountry = strCountry;
    }

    public String getMunicipality() {
        return mMunicipality;
    }

    public void setMunicipality(String strMunicipality) {
        this.mMunicipality = strMunicipality;
    }

    public String getPrefecture() {
        return mPrefecture;
    }

    public void setPrefecture(String strPrefecture) {
        this.mPrefecture = strPrefecture;
    }

    public String getCounty() {
        return mCounty;
    }

    public void setCounty(String strCounty) {
        this.mCounty = strCounty;
    }
}
