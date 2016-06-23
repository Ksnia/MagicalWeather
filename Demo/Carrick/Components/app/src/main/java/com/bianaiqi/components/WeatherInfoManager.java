package com.bianaiqi.components;

/**
 * Project：Components
 * Description：
 * Created by gsoft2-3 on 16-5-11
 *
 * @version ${VERSION}
 */
public class WeatherInfoManager {

    private static WeatherInfoManager sInstance;
    private CallBack mListener;

    public WeatherInfoManager() {

    }

    public static WeatherInfoManager getInstance(){
        if(null == sInstance){
            sInstance = new WeatherInfoManager();
        }
        return sInstance;
    }

    public void setWeatherInfoListener(CallBack callback){
        mListener = callback;
    }

    /**
     * 向网络端发起更新数据请求，待与Ray确认沟通
     */
    private void onWeatherInfoRequest(){

    }

    private void onWeatherInfoResponse(){
        if (null == mListener){
            mListener.onWeatherInfoUpdate();
        }
    }

    public interface CallBack {
        void onWeatherInfoUpdate();
    }
}
