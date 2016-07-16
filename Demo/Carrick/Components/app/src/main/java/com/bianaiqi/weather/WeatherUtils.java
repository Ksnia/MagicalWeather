package com.bianaiqi.weather;

import java.lang.reflect.Modifier;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

/**
 * Created by Carrick on 2016/7/12.
 */
public class WeatherUtils {

    private static Context mContext;
    private static Gson mGson;
    private static LocationManager mLocationManager;
    private static String locationProvider;
    private static Location l;

    public static void init(Context ctx) {
        if (ctx != null) {
            mContext = ctx;
            mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        }
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setGson(Gson gson) {

        if (gson != null && gson.equals(getGson())) {
            mGson = gson;
        }
    }

    public static Gson getGson() {
        if (mGson == null) {
            if (mContext != null) {
                mGson = new GsonBuilder().excludeFieldsWithModifiers(
                        Modifier.FINAL, Modifier.STATIC).create();
            }
        }
        return mGson;
    }

    public static Location getCurLocation() {
        if (mLocationManager != null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            criteria.setAltitudeRequired(false);
            criteria.setSpeedRequired(false);
            criteria.setCostAllowed(false);
            locationProvider = mLocationManager.getBestProvider(criteria, false);
            Looper.prepare();
            mLocationManager.requestLocationUpdates(locationProvider, 1000, 1, mLocationListener);
            l = mLocationManager.getLastKnownLocation(locationProvider);
        }
        return l;
    }

    public static final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //updateToNewLocation(location);
            l = location;
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

