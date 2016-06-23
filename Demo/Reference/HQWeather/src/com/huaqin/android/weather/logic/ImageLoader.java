package com.huaqin.android.weather.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.huaqin.android.weather.WeatherApplication;
import com.huaqin.android.weather.util.GlobalConstant;
import com.huaqin.android.weather.util.WebClientUtil;

public class ImageLoader {

    private Context mContext;

    public ImageLoader(Context context) {
        this.mContext = context;
    }

    public void excute(String strQuery) {
        if (!strQuery.startsWith("http")) {
            return;
        }
        File file = getFile(strQuery);
        if (file.exists()) {
            Log.d(GlobalConstant.TAG,
                    "File exists Image URI = " + Uri.fromFile(file));
            String str = Uri.fromFile(file).toString();
            Intent intent = new Intent();
            intent.setAction(GlobalConstant.BroadCastAction.WEATHER_UPDATE_IMGAE);
            intent.putExtra(GlobalConstant.IntentTag.INTENT_WEATHER_IMAGE, str);
            mContext.getApplicationContext().sendBroadcast(intent);
            return;
        }
        ImageTask task = new ImageTask();
        task.execute(strQuery);
    }

    private File getFile(String fileName) {
        File file = new File(
                ((WeatherApplication) mContext.getApplicationContext())
                        .getCatch(),
                fileName.substring(fileName.lastIndexOf(".")));
        return file;
    }

    class ImageTask extends AsyncTask<String, Integer, Uri> {

        @Override
        protected Uri doInBackground(String... params) {
            if (params == null) {
                return null;
            }
            File file = getFile(params[0]);
            Log.d(GlobalConstant.TAG, "file = " + file);
            String url = params[0].trim().replaceAll(" ", "%20");
            InputStream is = WebClientUtil.getInstance(mContext).query(url);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[1024];
            int len = 0;
            try {
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                is.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Uri.fromFile(file);
        }

        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result);
            Log.d(GlobalConstant.TAG, "Image URI = " + result);
            String str = result.toString();
            Intent intent = new Intent();
            intent.setAction(GlobalConstant.BroadCastAction.WEATHER_UPDATE_IMGAE);
            intent.putExtra(GlobalConstant.IntentTag.INTENT_WEATHER_IMAGE, str);
            mContext.getApplicationContext().sendBroadcast(intent);
        }
    }
}
