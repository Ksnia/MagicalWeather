package com.bianaiqi.components;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.bianaiqi.ui.CloudLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        ///M: Test for full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///M: Test for full Screen

        CloudLayout mCloudLayout = (CloudLayout) findViewById(R.id.cloud_layout);
    }
}

