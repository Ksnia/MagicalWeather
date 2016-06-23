package com.huaqin.android.weather.logic;
import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.huaqin.android.weather.model.City;
import com.huaqin.android.weather.util.GlobalConstant;
import com.huaqin.android.weather.util.WebClientUtil;
import com.huaqin.android.weather.util.XmlPullParserHelper;

public class CityLoader {

    private static final String URL_CITY_WOEID_ = "http://query.yahooapis.com/v1/public/yql?q=select * from geo.places where text='%s'";
    private Context mContext;
    private WoeidTaskLoadListener mListener;

    public CityLoader(Context context, WoeidTaskLoadListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    public void excute(String strQuery) {
        WoeidTask task = new WoeidTask();
        task.execute(strQuery);
    }

    class WoeidTask extends AsyncTask<String, Integer, List<City>> {

        @Override
        protected List<City> doInBackground(String... params) {
            if (params == null) {
                return null;
            }
	    List<City> listCity = null;
	    try{
            String url = String.format(URL_CITY_WOEID_, (Object[]) params)
                    .trim().replaceAll(" ", "%20");
            InputStream is = WebClientUtil.getInstance(mContext).query(url);
            listCity = XmlPullParserHelper.getCity(is,
                    GlobalConstant.CODE_FORMAT);
	    } catch (Exception e){
	        e.printStackTrace();
	    }
            return listCity;
        }

        @Override
        protected void onPostExecute(List<City> result) {
            super.onPostExecute(result);
            if (null != mListener) {
                mListener.onWoeidLoadFinished(result);
            }
        }
    }

    public interface WoeidTaskLoadListener {
        void onWoeidLoadFinished(List<City> result);
    }
}
