package com.bianaiqi.weather;

import java.lang.reflect.Modifier;
import java.util.List;

import com.bianaiqi.components.R;
import com.bianaiqi.util.MyLog;
import com.bianaiqi.util.RandomGenerator;
import com.bianaiqi.weather.data.local.WeatherCity;
import com.bianaiqi.weather.engine.EngineFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

/**
 * Created by Carrick on 2016/7/12.
 */
public class WeatherUtils {

    private static LocationManager mLocationMgr;
    private static Location mLocation;
    private static Gson mGson;
    private static String DEBUG_TAG = "Carrick_WeatherUtils";

    public static void setGson(Gson gson) {
        if (gson != null && gson.equals(getGson())) {
            mGson = gson;
        }
    }

    public static EngineFactory.EngineType getDefaultEngineType() {
        return EngineFactory.EngineType.YAHOO;
    }

    public static WeatherCity getDefaultCity(Context context) {
        String cityName = "";
        if (MyLog.DEBUG) {
            String[] cities = context.getResources().getStringArray(R.array.test_default_city);
            RandomGenerator rg = new RandomGenerator();
            cityName = cities[rg.getRandom(cities.length)];
        } else {
            cityName = context.getResources().getString(R.string.default_city);
        }
        String cityDomain = "";
        WeatherCity city = new WeatherCity(cityName, cityDomain);
        return city;
    }

    public static int getDefaultRequestType() {
        return WeatherConstant.QUERY_REQUEST_ITEM;
    }

    public static Gson getGson() {
        if (mGson == null) {
            mGson = new GsonBuilder().excludeFieldsWithModifiers(
                    Modifier.FINAL, Modifier.STATIC).create();

        }
        return mGson;
    }

    public static Location getCurLocation(Context context) {
        if (MyLog.DEBUG) {
            Log.d(DEBUG_TAG, "context = " + context);
        }
        if (null == context) {
            return null;
        }
        mLocationMgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (mLocationMgr != null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            criteria.setAltitudeRequired(false);
            criteria.setSpeedRequired(false);
            criteria.setCostAllowed(false);
            String locationProvider = mLocationMgr.getBestProvider(criteria, false);
            if (MyLog.DEBUG) {
                Log.d(DEBUG_TAG, "locationProvider = " + locationProvider);
            }

            Looper.prepare();
            mLocationMgr.requestLocationUpdates(locationProvider, 1000, 1, mLocationListener);
            mLocation = mLocationMgr.getLastKnownLocation(locationProvider);
        }
        if (MyLog.DEBUG) {
            Log.d(DEBUG_TAG, "mLocationMgr = " + mLocationMgr + "; mLocation = " + mLocation);
        }
        return mLocation;
    }

    public static final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //updateToNewLocation(location);
            mLocation = location;
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };
}
