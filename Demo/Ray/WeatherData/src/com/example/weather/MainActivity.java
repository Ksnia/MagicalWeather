package com.example.weather;

import com.example.weather.thread.SendWebRequestThread;
import com.example.weather.util.AppUtil;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button startBtn, stopBtn;
	private TextView message;
	private static final String TAG = "MainActivity"; 
	public static final int QUERY_RESPONSE = 1; 
	private Handler  mHandler =  new Handler(){

	
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			 Log.d(TAG,
	                    "mHandler.handleMessage, what = " + msg.what + ",hashcode:"
	                            + mHandler.hashCode());
			 Bundle bundle = null;
			 switch (msg.what) {
			   case QUERY_RESPONSE:
				   bundle = msg.getData();
				   String mg = (String)bundle.get("key_info");
				   message.setText(mg);
				   break;
			   default:
				   break;
			 
			 
			 }
			 
		}
		
		
		
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppUtil.setContext(this);
		setContentView(R.layout.activity_main);
		message = (TextView)findViewById(R.id.message);
		startBtn = (Button)findViewById(R.id.startQuery);
		stopBtn = (Button)findViewById(R.id.stopQuery);		
		startBtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SendWebRequestThread sendThread = new SendWebRequestThread("", "", mHandler);
				sendThread.start();				 
			}
			
		});
		
		
		
	}

	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}



   










	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
