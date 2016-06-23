package com.huaqin.android.weather.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;

public class WebClientUtil {

    private static WebClientUtil mInstance;

    public static WebClientUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new WebClientUtil();
        }
        return mInstance;
    }

    public InputStream query(String url) {
        LogEx.d("query url = " + url);
        HttpGet httpGet = new HttpGet(url);
        HttpResponse res = null;
        try {
            res = new DefaultHttpClient().execute(httpGet);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (res != null && res.getStatusLine().getStatusCode() == 200) {
            InputStream localInputStream;
            try {
                localInputStream = res.getEntity().getContent();
                return localInputStream;
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
