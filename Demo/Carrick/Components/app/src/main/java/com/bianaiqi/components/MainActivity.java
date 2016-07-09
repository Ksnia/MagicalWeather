package com.bianaiqi.components;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bianaiqi.ui.CloudLayout;

public class MainActivity extends Activity {

    private ImageView mImageViewWeatherIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        ///M: Test for full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///M: Test for full Screen

        ImageView mImageViewWeatherIcon = (ImageView) findViewById(R.id.weather_icon);
        mImageViewWeatherIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CloudLayout mCloudLayout = (CloudLayout) findViewById(R.id.cloud_layout);
            }
        });
    }
}

